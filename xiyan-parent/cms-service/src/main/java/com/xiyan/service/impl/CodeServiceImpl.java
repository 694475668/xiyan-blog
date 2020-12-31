package com.xiyan.service.impl;

import cn.hutool.core.collection.IterUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiyan.bo.CodeBO;
import com.xiyan.domain.AttentionDO;
import com.xiyan.domain.CodeDO;
import com.xiyan.domain.DownloadDO;
import com.xiyan.domain.UserDO;
import com.xiyan.dto.*;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.AttentionMapper;
import com.xiyan.mapper.CodeMapper;
import com.xiyan.mapper.DownloadMapper;
import com.xiyan.mapper.UserMapper;
import com.xiyan.repository.CodeRepository;
import com.xiyan.service.CodeService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.*;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: bright
 * @date:Created in 2020/11/29 13:48
 * @describe :
 */
@Service
public class CodeServiceImpl implements CodeService {

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AttentionMapper attentionMapper;

    @Resource
    private DownloadMapper downloadMapper;

    @Resource
    private CodeRepository codeRepository;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public CommonListVO<CodeVO> tagList() {
        CommonListVO commonList = new CommonListVO();
        //获取redis数据
        if (redisTemplate.hasKey("tag")) {
            String data = redisTemplate.opsForValue().get("tag");
            return JSONArray.parseObject(data, CommonListVO.class);
        }
        List<CodeDO> tag = codeMapper.queryTag();
        List<CodeVO> list = ObjectConvertUtil.convertInstance().objectConvert(tag, CodeVO.class);
        commonList.setVoList(list);
        //写入redis缓存 失效时间一天
        redisTemplate.opsForValue().set("tag", JSONArray.toJSONString(commonList), 1, TimeUnit.DAYS);
        return commonList;
    }

    @Override
    public CommonListVO<CodeVO> list(SortDTO sortDTO) {
        CommonListVO commonListVO = new CommonListVO();

        //判断es是否有数据
        Iterable<CodeBO> iterable = codeRepository.findAll();
        if (IterUtil.isEmpty(iterable)) {
            List<CodeBO> codeBOList = new ArrayList<>();
            //没有数据就需要把数据库的数据插入到es中
            List<CodeDO> codeDOList = codeMapper.selectList(new QueryWrapper<CodeDO>().eq("state", "1"));
            for (CodeDO codeDO : codeDOList) {
                CodeBO codeBO = new CodeBO();
                BeanUtils.copyProperties(codeDO, codeBO);
                codeBOList.add(codeBO);
            }
            codeRepository.saveAll(codeBOList);
        }
        //分页
        Pageable pageable = PageRequest.of(sortDTO.getPageNo() - 1, sortDTO.getPageSize());
        //排序
        SortBuilder<FieldSortBuilder> sortBuilder = new FieldSortBuilder(sortDTO.getSortField()).order(SortOrder.DESC);

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withSort(sortBuilder)
                .build();
        //es查询
        SearchHits<CodeBO> searchHits = elasticsearchRestTemplate.search(query, CodeBO.class);
        //总条数
        commonListVO.setTotal(searchHits.getTotalHits());
        //分页查询到的数据
        List<SearchHit<CodeBO>> searchHitList = searchHits.getSearchHits();
        List<CodeVO> codeVOList = new ArrayList<>();
        List<String> tagList = null;
        CodeVO codeVO = null;
        //Object转对象
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit h : searchHitList) {
            CodeBO codeBO = objectMapper.convertValue(h.getContent(), CodeBO.class);
            codeVO = new CodeVO();
            UserDO user = userMapper.selectById(codeBO.getUserId());
            BeanUtils.copyProperties(codeBO, codeVO);
            codeVO.setName(user.getName());
            //标签拆分
            String[] split = codeBO.getTag().split(",");
            //数组转换集合
            tagList = Arrays.asList(split);
            codeVO.setTagList(tagList);
            codeVOList.add(codeVO);
            commonListVO.setVoList(codeVOList);
        }
        return commonListVO;
    }

    @Override
    public BaseVO getCodeById(Integer id) {
        CodeBO codeBO = codeRepository.findArticleById(id);
        CodeByIdVO codeByIdVO = new CodeByIdVO();
        BeanUtils.copyProperties(codeBO, codeByIdVO);
        UserDO user = userMapper.selectById(codeBO.getUserId());
        codeByIdVO.setName(user.getName());
        codeByIdVO.setUsername(user.getUsername());
        //标签拆分
        String[] split = codeBO.getTag().split(",");
        List<String> tagList = Arrays.asList(split);
        codeByIdVO.setTagList(tagList);
        //所属用户
        UserDO userDO = userMapper.selectById(codeBO.getUserId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        //粉丝数
        Integer attentionCount = attentionMapper.selectCount(new QueryWrapper<AttentionDO>().eq("attention_user_id", codeBO.getUserId()));
        userVO.setAttentionCount(attentionCount);
        //发布数
        Integer codeCount = codeMapper.selectCount(new QueryWrapper<CodeDO>().eq("user_id", codeBO.getUserId()));
        userVO.setCodeCount(codeCount);
        codeByIdVO.setUser(userVO);
        return codeByIdVO;
    }

    @Override
    public BaseVO browse(Integer id) {
        codeMapper.browse(id);
        //修改es数据
        CodeBO code = codeRepository.findArticleById(id);
        code.setReadCount(code.getReadCount() + 1);
        codeRepository.save(code);
        return new BaseVO();
    }

    @Override
    public BaseVO like(Integer id) {
        codeMapper.like(id);
        //修改es数据
        CodeBO code = codeRepository.findArticleById(id);
        code.setStarCount(code.getStarCount() + 1);
        codeRepository.save(code);
        return new BaseVO();
    }

    @Override
    public BaseVO add(CodeAddDTO codeAddDTO, Integer userId) {
        CodeDO codeDO = new CodeDO();
        BeanUtils.copyProperties(codeAddDTO, codeDO);
        codeDO.setUserId(userId);
        codeDO.setCreateTime(new Date());
        codeDO.setUpdateTime(new Date());
        codeMapper.insert(codeDO);
        return new BaseVO();
    }

    @Override
    public BaseVO update(CodeUpdateDTO codeUpdateDTO, Integer id) {
        CodeDO codeDO = new CodeDO();
        BeanUtils.copyProperties(codeUpdateDTO, codeDO);
        codeDO.setUpdateTime(new Date());
        codeDO.setId(id);
        codeMapper.updateById(codeDO);
        //修改es数据
        CodeBO code = codeRepository.findArticleById(id);
        code.setTitle(codeUpdateDTO.getTitle());
        code.setPic(codeUpdateDTO.getPic());
        code.setTag(codeUpdateDTO.getTag());
        code.setRemark(codeUpdateDTO.getRemark());
        code.setContent(codeUpdateDTO.getContent());
        code.setType(codeUpdateDTO.getType());
        code.setMarkdownContent(codeUpdateDTO.getMarkdownContent());
        code.setMarkdownType(codeUpdateDTO.getMarkdownType());
        codeRepository.save(code);
        return new BaseVO();
    }

    @Override
    public BaseVO download(CodeDownloadDTO codeDownloadDTO, Integer id) {
        UserDO userDO = userMapper.selectById(id);
        CodeDownloadVO codeDownloadVO = new CodeDownloadVO();
        CodeDO codeDO = codeMapper.selectById(codeDownloadDTO.getId());
        UserDO userById = userMapper.selectById(codeDO.getUserId());
        //下载过的不需要扣金币了，直接就可以下载
        DownloadDO downloadDO = downloadMapper.selectOne(new QueryWrapper<DownloadDO>().and(downloadDOQueryWrapper -> downloadDOQueryWrapper.eq("user_id", id).eq("code_id", codeDownloadDTO.getId())));
        if (null != downloadDO) {
            codeDownloadVO.setUrl(codeDO.getDownloadUrl());
            codeDownloadVO.setPoint(userDO.getPoint());
            return codeDownloadVO;
        }
        //自己下载自己发布的源码
        if (id == codeDO.getUserId()) {
            codeDownloadVO.setUrl(codeDO.getDownloadUrl());
            codeDownloadVO.setPoint(userDO.getPoint());
        }
        //会员直接下载
        if ("1".equals(userDO.getIsMember()) && id != codeDO.getUserId()) {
            codeDownloadVO.setUrl(codeDO.getDownloadUrl());
            codeDownloadVO.setPoint(userDO.getPoint());
            UserDO user = new UserDO();
            //发布人增加金币
            user = new UserDO();
            user.setId(codeDO.getUserId());
            user.setPoint(userById.getPoint() + codeDO.getDownloadPoint());
            userMapper.updateById(user);
        } else {
            if (id != codeDO.getUserId()) {
                //金币不足
                if (userDO.getPoint() < codeDO.getDownloadPoint()) {
                    return new BaseVO(false, ErrorCodeEnum.E0712.getKey(), ErrorCodeEnum.E0712.getValue());
                } else {
                    codeDownloadVO.setUrl(codeDO.getDownloadUrl());
                    //修改用户金币
                    UserDO user = new UserDO();
                    user.setPoint(userDO.getPoint() - codeDO.getDownloadPoint());
                    user.setId(id);
                    userMapper.updateById(user);
                    //发布人增加金币
                    user = new UserDO();
                    user.setId(codeDO.getUserId());
                    user.setPoint(userById.getPoint() + codeDO.getDownloadPoint());
                    userMapper.updateById(user);
                    codeDownloadVO.setPoint(userDO.getPoint() - codeDO.getDownloadPoint());
                }
            }
        }

        //添加下载记录
        downloadDO = new DownloadDO();
        downloadDO.setUserId(id);
        downloadDO.setCreateTime(new Date());
        downloadDO.setCodeId(codeDownloadDTO.getId());
        downloadMapper.insert(downloadDO);
        //修改源码的下载数
        Integer count = downloadMapper.selectCount(new QueryWrapper<DownloadDO>().eq("code_id", codeDownloadDTO.getId()));
        codeDO = new CodeDO();
        codeDO.setId(codeDownloadDTO.getId());
        codeDO.setDownloadCount(count);
        codeMapper.updateById(codeDO);
        //修改es数据
        CodeBO code = codeRepository.findArticleById(codeDownloadDTO.getId());
        code.setDownloadCount(count);
        codeRepository.save(code);
        return codeDownloadVO;
    }

    @Override
    public BaseVO review(CodeReviewDTO codeReviewDTO, Integer id) {
        CodeDO codeDO = new CodeDO();
        codeDO.setId(id);
        codeDO.setState(codeReviewDTO.getState());
        codeMapper.updateById(codeDO);
        //插入es  由于有些字段是数据库默认赋值，所以我这里需要查询一遍在插入到ES中
        CodeDO article = codeMapper.selectById(codeDO.getId());
        CodeBO codeBO = new CodeBO();
        BeanUtils.copyProperties(article, codeBO);
        codeRepository.save(codeBO);
        return new BaseVO();
    }
}
