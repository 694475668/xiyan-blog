package com.xiyan.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyan.bo.ArticleBO;
import com.xiyan.constants.Constant;
import com.xiyan.domain.ArticleDO;
import com.xiyan.domain.AttentionDO;
import com.xiyan.domain.CodeDO;
import com.xiyan.dto.ArticleDTO;
import com.xiyan.dto.ArticleUpdateDTO;
import com.xiyan.dto.GetUserDTO;
import com.xiyan.dto.SortDTO;
import com.xiyan.feign.UserFeign;
import com.xiyan.mapper.ArticleMapper;
import com.xiyan.mapper.AttentionMapper;
import com.xiyan.mapper.CodeMapper;
import com.xiyan.repository.ArticleRepository;
import com.xiyan.service.ArticleService;
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

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/1 13:43
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UserFeign userFeign;

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private AttentionMapper attentionMapper;

    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    public CommonListVO<ArticleVO> list(SortDTO sortDTO) {
        CommonListVO commonListVO = new CommonListVO();
        Page<ArticleDO> articleDOPage = new Page<>(sortDTO.getPageNo(), sortDTO.getPageSize());
        QueryWrapper<ArticleDO> articleDOQueryWrapper = new QueryWrapper<ArticleDO>().and(queryWrapper -> queryWrapper.eq("state", "1").eq("type", sortDTO.getType()))
                .orderByDesc(sortDTO.getSortField());
        //出现type为null的时候
        if (StringUtils.isEmpty(sortDTO.getType())) {
            articleDOQueryWrapper = new QueryWrapper<ArticleDO>().and(queryWrapper -> queryWrapper.eq("state", "1"))
                    .orderByDesc(sortDTO.getSortField());
        }
        //查询
        articleDOPage = articleMapper.selectPage(articleDOPage, articleDOQueryWrapper);
        //获取查询的数据
        List<ArticleDO> articleDOList = articleDOPage.getRecords();
        //总条数
        commonListVO.setTotal(articleDOPage.getTotal());

        List<ArticleVO> articleVOList = new ArrayList<>();
        List<String> tagList = null;
        ArticleVO articleVO = null;
        for (ArticleDO articleDO : articleDOList) {
            articleVO = new ArticleVO();
            UserByIdVO user = null;
            if (redisTemplate.hasKey("user_" + articleDO.getUserId())) {
                String data = redisTemplate.opsForValue().get("user_" + articleDO.getUserId());
                user = JSONArray.parseObject(data, UserByIdVO.class);
            } else {
                user = userFeign.getUser(new GetUserDTO(articleDO.getUserId(), null));
                //写入redis缓存
                redisTemplate.opsForValue().set("user_" + articleDO.getUserId(), JSONArray.toJSONString(user));
            }
            log.info("用户信息【{}】", user);
            BeanUtils.copyProperties(articleDO, articleVO);
            articleVO.setName(user.getName());
            articleVO.setImage(user.getPhoto());
            //标签拆分
            String[] split = articleDO.getTag().split(",");
            //数组转换集合
            tagList = Arrays.asList(split);
            articleVO.setTagList(tagList);
            articleVOList.add(articleVO);
            commonListVO.setVoList(articleVOList);
        }
        return commonListVO;
    }

    @Override
    public BaseVO getArticleById(Integer id) {
        ArticleByIdVO articleByIdVO = new ArticleByIdVO();
        ArticleDO articleDO = articleMapper.selectById(id);
        BeanUtils.copyProperties(articleDO, articleByIdVO);
        UserByIdVO user = userFeign.getUser(new GetUserDTO(articleDO.getUserId(), null));
        articleByIdVO.setName(user.getName());
        articleByIdVO.setUsername(user.getUsername());
        //添加wangEditor前端代码块显示样式
        if (articleDO.getMarkdownType().equals(Constant.WANG_TYPE)) {
            articleByIdVO.setContent(articleDO.getContent().replaceAll("<pre", "<pre style=\"display:block;overflow-x:auto;padding:0.9em 1.5em;background-color:#282a36;color:#f8f8f2;font-family: Source Code Pro,DejaVu Sans Mono,Ubuntu Mono,Anonymous Pro,Droid Sans Mono,Menlo,Monaco,Consolas,Inconsolata,Courier,monospace,PingFang SC,Microsoft YaHei,sans-serif;line-height: 22px;\""));
        }
        //标签拆分
        String[] split = articleDO.getTag().split(",");
        List<String> tagList = Arrays.asList(split);
        articleByIdVO.setTagList(tagList);
        //所属用户
        UserByIdVO userById = null;
        if (redisTemplate.hasKey("user_" + articleDO.getUserId())) {
            String data = redisTemplate.opsForValue().get("user_" + articleDO.getUserId());
            userById = JSONArray.parseObject(data, UserByIdVO.class);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userById, userVO);
        //粉丝数
        Integer attentionCount = attentionMapper.selectCount(new QueryWrapper<AttentionDO>().eq("attention_user_id", articleDO.getUserId()));
        userVO.setAttentionCount(attentionCount);
        //发布数
        Integer codeCount = codeMapper.selectCount(new QueryWrapper<CodeDO>().eq("user_id", articleDO.getUserId()));
        userVO.setCodeCount(codeCount);
        articleByIdVO.setUser(userVO);
        return articleByIdVO;
    }

    @Override
    public BaseVO add(ArticleDTO articleDTO, Integer userId) {
        ArticleDO articleDO = new ArticleDO();
        BeanUtils.copyProperties(articleDTO, articleDO);
        articleDO.setUserId(userId);
        articleDO.setCreateTime(new Date());
        articleDO.setUpdateTime(new Date());
        articleMapper.insert(articleDO);
        return new BaseVO();
    }

    @Override
    public BaseVO browse(Integer id) {
        articleMapper.browse(id);
        //修改es数据
        ArticleBO article = articleRepository.findArticleById(id);
        article.setReadCount(article.getReadCount() + 1);
        articleRepository.save(article);
        return new BaseVO();
    }

    @Override
    public BaseVO like(Integer id) {
        articleMapper.like(id);
        //修改es数据
        ArticleBO article = articleRepository.findArticleById(id);
        article.setStarCount(article.getStarCount() + 1);
        articleRepository.save(article);
        return new BaseVO();
    }

    @Override
    public BaseVO update(ArticleUpdateDTO articleUpdateDTO, Integer id) {
        ArticleDO articleDO = new ArticleDO();
        BeanUtils.copyProperties(articleUpdateDTO, articleDO);
        articleDO.setUpdateTime(new Date());
        articleDO.setId(id);
        articleMapper.updateById(articleDO);
        //修改es数据
        ArticleBO article = articleRepository.findArticleById(id);
        article.setTitle(articleUpdateDTO.getTitle());
        article.setPic(articleUpdateDTO.getPic());
        article.setTag(articleUpdateDTO.getTag());
        article.setRemark(articleUpdateDTO.getRemark());
        article.setContent(articleUpdateDTO.getContent());
        article.setType(articleUpdateDTO.getType());
        article.setMarkdownContent(articleUpdateDTO.getMarkdownContent());
        article.setMarkdownType(articleUpdateDTO.getMarkdownType());
        articleRepository.save(article);
        return new BaseVO();
    }
}
