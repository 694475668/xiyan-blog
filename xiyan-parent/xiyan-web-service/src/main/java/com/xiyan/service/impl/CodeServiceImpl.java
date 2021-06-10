package com.xiyan.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyan.bo.CodeBO;
import com.xiyan.constants.Constant;
import com.xiyan.domain.AttentionDO;
import com.xiyan.domain.CodeDO;
import com.xiyan.domain.DownloadDO;
import com.xiyan.dto.*;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.feign.UserFeign;
import com.xiyan.mapper.AttentionMapper;
import com.xiyan.mapper.CodeMapper;
import com.xiyan.mapper.DownloadMapper;
import com.xiyan.repository.CodeRepository;
import com.xiyan.service.CodeService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
@Slf4j
public class CodeServiceImpl implements CodeService {

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private UserFeign userFeign;

    @Resource
    private AttentionMapper attentionMapper;

    @Resource
    private DownloadMapper downloadMapper;

    @Resource
    private CodeRepository codeRepository;

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
        Page<CodeDO> codeDOPage = new Page<>(sortDTO.getPageNo(), sortDTO.getPageSize());
        QueryWrapper<CodeDO> codeDOQueryWrapper = new QueryWrapper<CodeDO>().and(queryWrapper -> queryWrapper.eq("state", "1").eq("type", sortDTO.getType()))
                .orderByDesc(sortDTO.getSortField());
        //出现type为null的时候
        if (StringUtils.isEmpty(sortDTO.getType())) {
            codeDOQueryWrapper = new QueryWrapper<CodeDO>().and(queryWrapper -> queryWrapper.eq("state", "1"))
                    .orderByDesc(sortDTO.getSortField());
        }
        //查询
        codeDOPage = codeMapper.selectPage(codeDOPage, codeDOQueryWrapper);
        //获取查询的数据
        List<CodeDO> codeDOList = codeDOPage.getRecords();
        //总条数
        commonListVO.setTotal(codeDOPage.getTotal());
        List<CodeVO> codeVOList = new ArrayList<>();
        List<String> tagList = null;
        CodeVO codeVO = null;
        for (CodeDO codeDO : codeDOList) {
            codeVO = new CodeVO();
            UserByIdVO user = userFeign.getUser(new GetUserDTO(codeDO.getUserId(), null));
            if (redisTemplate.hasKey("user_" + codeDO.getUserId())) {
                String data = redisTemplate.opsForValue().get("user_" + codeDO.getUserId());
                user = JSONArray.parseObject(data, UserByIdVO.class);
            } else {
                user = userFeign.getUser(new GetUserDTO(codeDO.getUserId(), null));
                //写入redis缓存
                redisTemplate.opsForValue().set("user_" + codeDO.getUserId(), JSONArray.toJSONString(user));
            }
            log.info("用户信息【{}】", user);
            BeanUtils.copyProperties(codeDO, codeVO);
            codeVO.setName(user.getName());
            //标签拆分
            String[] split = codeDO.getTag().split(",");
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
        CodeDO codeDO = codeMapper.selectById(id);
        CodeByIdVO codeByIdVO = new CodeByIdVO();
        BeanUtils.copyProperties(codeDO, codeByIdVO);
        UserByIdVO user = userFeign.getUser(new GetUserDTO(codeDO.getUserId(), null));
        codeByIdVO.setName(user.getName());
        codeByIdVO.setUsername(user.getUsername());
        //添加wangEditor前端样式
        if (codeDO.getMarkdownType().equals(Constant.WANG_TYPE)) {
            codeByIdVO.setContent(codeDO.getContent().replaceAll("<pre", "<pre style=\"display:block;overflow-x:auto;padding:0.9em 1.5em;background-color:#282a36;color:#f8f8f2;font-family: Source Code Pro,DejaVu Sans Mono,Ubuntu Mono,Anonymous Pro,Droid Sans Mono,Menlo,Monaco,Consolas,Inconsolata,Courier,monospace,PingFang SC,Microsoft YaHei,sans-serif;line-height: 22px;\""));
        }
        //标签拆分
        String[] split = codeDO.getTag().split(",");
        List<String> tagList = Arrays.asList(split);
        codeByIdVO.setTagList(tagList);
        //所属用户
        UserByIdVO userById = null;
        if (redisTemplate.hasKey("user_" + codeDO.getUserId())) {
            String data = redisTemplate.opsForValue().get("user_" + codeDO.getUserId());
            userById = JSONArray.parseObject(data, UserByIdVO.class);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userById, userVO);
        //粉丝数
        Integer attentionCount = attentionMapper.selectCount(new QueryWrapper<AttentionDO>().eq("attention_user_id", codeDO.getUserId()));
        userVO.setAttentionCount(attentionCount);
        //发布数
        Integer codeCount = codeMapper.selectCount(new QueryWrapper<CodeDO>().eq("user_id", codeDO.getUserId()));
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
        UserByIdVO userByIdVO = userFeign.getUser(new GetUserDTO(id, null));
        CodeDownloadVO codeDownloadVO = new CodeDownloadVO();
        CodeDO codeDO = codeMapper.selectById(codeDownloadDTO.getId());
        UserByIdVO userById = userFeign.getUser(new GetUserDTO(codeDO.getUserId(), null));
        //下载过的不需要扣金币了，直接就可以下载
        DownloadDO downloadDO = downloadMapper.selectOne(new QueryWrapper<DownloadDO>().and(downloadDOQueryWrapper -> downloadDOQueryWrapper.eq("user_id", id).eq("code_id", codeDownloadDTO.getId())));
        if (null != downloadDO) {
            codeDownloadVO.setUrl(codeDO.getDownloadUrl());
            codeDownloadVO.setPoint(userByIdVO.getPoint());
            return codeDownloadVO;
        }
        //自己下载自己发布的源码
        if (id == codeDO.getUserId()) {
            codeDownloadVO.setUrl(codeDO.getDownloadUrl());
            codeDownloadVO.setPoint(userByIdVO.getPoint());
        }
        //会员直接下载
        if ("1".equals(userByIdVO.getIsMember()) && id != codeDO.getUserId()) {
            codeDownloadVO.setUrl(codeDO.getDownloadUrl());
            codeDownloadVO.setPoint(userByIdVO.getPoint());
            UserUpdateDTO userUpdateDTO = null;
            //发布人增加金币
            userUpdateDTO = new UserUpdateDTO();
            userUpdateDTO.setId(codeDO.getUserId());
            userUpdateDTO.setPoint(userById.getPoint() + codeDO.getDownloadPoint());
            userFeign.update(userUpdateDTO);
        } else {
            if (id != codeDO.getUserId()) {
                //金币不足
                if (userByIdVO.getPoint() < codeDO.getDownloadPoint()) {
                    return new BaseVO(false, ErrorCodeEnum.E0705.getKey(), ErrorCodeEnum.E0705.getValue());
                } else {
                    codeDownloadVO.setUrl(codeDO.getDownloadUrl());
                    //修改用户金币
                    UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
                    userUpdateDTO.setPoint(userByIdVO.getPoint() - codeDO.getDownloadPoint());
                    userUpdateDTO.setId(id);
                    userFeign.update(userUpdateDTO);
                    //发布人增加金币
                    userUpdateDTO = new UserUpdateDTO();
                    userUpdateDTO.setId(codeDO.getUserId());
                    userUpdateDTO.setPoint(userById.getPoint() + codeDO.getDownloadPoint());
                    userFeign.update(userUpdateDTO);
                    codeDownloadVO.setPoint(userByIdVO.getPoint() - codeDO.getDownloadPoint());
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
}
