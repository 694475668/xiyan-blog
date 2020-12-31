package com.xiyan.service.impl;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiyan.bo.ArticleBO;
import com.xiyan.domain.ArticleDO;
import com.xiyan.domain.AttentionDO;
import com.xiyan.domain.CodeDO;
import com.xiyan.domain.UserDO;
import com.xiyan.dto.ArticleDTO;
import com.xiyan.dto.ArticleReviewDTO;
import com.xiyan.dto.ArticleUpdateDTO;
import com.xiyan.dto.SortDTO;
import com.xiyan.mapper.ArticleMapper;
import com.xiyan.mapper.AttentionMapper;
import com.xiyan.mapper.CodeMapper;
import com.xiyan.mapper.UserMapper;
import com.xiyan.repository.ArticleRepository;
import com.xiyan.service.ArticleService;
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
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UserMapper userMapper;

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
        //判断es是否有数据
        Iterable<ArticleBO> iterable = articleRepository.findAll();
        if (IterUtil.isEmpty(iterable)) {
            List<ArticleBO> articleBOList = new ArrayList<>();
            //没有数据就需要把数据库的数据插入到es中
            List<ArticleDO> articleDOList = articleMapper.selectList(new QueryWrapper<ArticleDO>().eq("state", "1"));
            for (ArticleDO articleDO : articleDOList) {
                ArticleBO articleBO = new ArticleBO();
                BeanUtils.copyProperties(articleDO, articleBO);
                articleBOList.add(articleBO);
            }
            articleRepository.saveAll(articleBOList);
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
        SearchHits<ArticleBO> searchHits = elasticsearchRestTemplate.search(query, ArticleBO.class);
        //总条数
        commonListVO.setTotal(searchHits.getTotalHits());
        //分页查询到的数据
        List<SearchHit<ArticleBO>> searchHitList = searchHits.getSearchHits();
        List<ArticleVO> articleVOList = new ArrayList<>();
        List<String> tagList = null;
        ArticleVO articleVO = null;
        //Object转对象
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit h : searchHitList) {
            ArticleBO articleBO = objectMapper.convertValue(h.getContent(), ArticleBO.class);
            articleVO = new ArticleVO();
            UserDO user = userMapper.selectById(articleBO.getUserId());
            BeanUtils.copyProperties(articleBO, articleVO);
            articleVO.setName(user.getName());
            //标签拆分
            String[] split = articleBO.getTag().split(",");
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
        ArticleBO articleBO = articleRepository.findArticleById(id);
        BeanUtils.copyProperties(articleBO, articleByIdVO);
        UserDO user = userMapper.selectById(articleBO.getUserId());
        articleByIdVO.setName(user.getName());
        articleByIdVO.setUsername(user.getUsername());
        //标签拆分
        String[] split = articleBO.getTag().split(",");
        List<String> tagList = Arrays.asList(split);
        articleByIdVO.setTagList(tagList);
        UserDO userDO = userMapper.selectById(articleBO.getUserId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        //粉丝数
        Integer attentionCount = attentionMapper.selectCount(new QueryWrapper<AttentionDO>().eq("attention_user_id", articleBO.getUserId()));
        userVO.setAttentionCount(attentionCount);
        //发布数
        Integer codeCount = codeMapper.selectCount(new QueryWrapper<CodeDO>().eq("user_id", articleBO.getUserId()));
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
