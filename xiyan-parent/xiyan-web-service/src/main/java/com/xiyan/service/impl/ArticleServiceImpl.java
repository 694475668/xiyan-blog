package com.xiyan.service.impl;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyan.bo.ArticleBO;
import com.xiyan.constants.Constant;
import com.xiyan.domain.ArticleDO;
import com.xiyan.domain.AttentionDO;
import com.xiyan.domain.CodeDO;
import com.xiyan.dto.*;
import com.xiyan.feign.UserFeign;
import com.xiyan.mapper.ArticleMapper;
import com.xiyan.mapper.AttentionMapper;
import com.xiyan.mapper.CodeMapper;
import com.xiyan.repository.ArticleRepository;
import com.xiyan.service.ArticleService;
import com.xiyan.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
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
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    public CommonListVO<ArticleVO> list(SortDTO sortDTO) {
        CommonListVO commonListVO = new CommonListVO();
        boolean exists = elasticsearchRestTemplate.indexOps(ArticleBO.class).exists();
        log.info("es索引存在状态【{}】", elasticsearchRestTemplate.indexOps(ArticleBO.class).exists());
        if (!exists) {
            //创建索引
            elasticsearchRestTemplate.indexOps(ArticleBO.class).create();
            //判断es数据是否为空
            log.info("es数据【{}】", IterUtil.isEmpty(articleRepository.findAll()));
            if (IterUtil.isEmpty(articleRepository.findAll())) {
                List<ArticleDO> articleDOList = articleMapper.selectList(new QueryWrapper<ArticleDO>().eq("state", "1"));
                List<ArticleBO> articleBOList = new ArrayList<>();
                for (ArticleDO articleDO : articleDOList) {
                    ArticleBO articleBO = new ArticleBO();
                    BeanUtils.copyProperties(articleDO, articleBO);
                    articleBOList.add(articleBO);
                }
                //插入到es中
                articleRepository.saveAll(articleBOList);
            }
        }
        Page<ArticleDO> articleDOPage = new Page<>(sortDTO.getPageNo(), sortDTO.getPageSize());
        QueryWrapper<ArticleDO> articleDOQueryWrapper = new QueryWrapper<ArticleDO>().and(queryWrapper -> queryWrapper.eq("state", "1").like("type", sortDTO.getType()))
                .orderByDesc(sortDTO.getSortField());
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
            UserByIdVO user = userFeign.getUser(new GetUserDTO(articleDO.getUserId(), null));
            log.info("用户信息【{}】", user);
            BeanUtils.copyProperties(articleDO, articleVO);
            articleVO.setName(user.getName());
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
        UserByIdVO userById = userFeign.getUser(new GetUserDTO(articleDO.getUserId(), null));
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

    @Override
    public BaseVO review(ArticleReviewDTO articleReviewDTO, Integer id) {
        ArticleDO articleDO = new ArticleDO();
        articleDO.setId(id);
        articleDO.setState(articleReviewDTO.getState());
        articleMapper.updateById(articleDO);
        //插入es  由于有些字段是数据库默认赋值，所以我这里需要查询一遍在插入到ES中
        ArticleDO article = articleMapper.selectById(articleDO.getId());
        ArticleBO articleBO = new ArticleBO();
        BeanUtils.copyProperties(article, articleBO);
        articleRepository.save(articleBO);
        return new BaseVO();
    }
}
