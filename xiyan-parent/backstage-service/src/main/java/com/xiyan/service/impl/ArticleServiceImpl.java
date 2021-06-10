package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.bo.ArticleBO;
import com.xiyan.domain.ArticleDO;
import com.xiyan.dto.ArticleReviewDTO;
import com.xiyan.dto.ArticleUpdateDTO;
import com.xiyan.dto.GetUserDTO;
import com.xiyan.dto.SortDTO;
import com.xiyan.feign.UserFeign;
import com.xiyan.mapper.ArticleMapper;
import com.xiyan.repository.ArticleRepository;
import com.xiyan.service.ArticleService;
import com.xiyan.vo.ArticleVO;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.UserByIdVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private ArticleRepository articleRepository;

    @Resource
    private UserFeign userFeign;


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

    public CommonListVO<ArticleVO> list(SortDTO sortDTO) {
        CommonListVO commonListVO = new CommonListVO();
        //解决初始参数解密后空数据是null问题,在做数据操作时候就会出现空指针异常
        if (StringUtils.isEmpty(sortDTO.getTitle())) {
            sortDTO.setTitle("");
        }
        if (StringUtils.isEmpty(sortDTO.getState())) {
            sortDTO.setState("");
        }
        QueryWrapper<ArticleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sortDTO.getTitle()), "title", sortDTO.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(sortDTO.getState()), "state", sortDTO.getState());
        queryWrapper.orderByDesc("create_time");
        //获取查询的数据
        List<ArticleDO> articleDOList = articleMapper.selectList(queryWrapper);
        int count = articleDOList.size();
        //总条数
        commonListVO.setTotal(Long.valueOf(count));
        //每页的开始数
        sortDTO.setPageNo((sortDTO.getPageNo() - 1) * sortDTO.getPageSize());
        articleDOList = articleDOList.subList(sortDTO.getPageNo(), count - sortDTO.getPageNo() > sortDTO.getPageSize() ? sortDTO.getPageNo() + sortDTO.getPageSize() : count);
        List<ArticleVO> articleVOList = new ArrayList<>();
        ArticleVO articleVO = null;
        for (ArticleDO articleDO : articleDOList) {
            articleVO = new ArticleVO();
            UserByIdVO user = userFeign.getUser(new GetUserDTO(articleDO.getUserId(), null));
            log.info("用户信息【{}】", user);
            BeanUtils.copyProperties(articleDO, articleVO);
            articleVO.setName(user.getName());
            articleVOList.add(articleVO);
            commonListVO.setVoList(articleVOList);
        }
        return commonListVO;
    }

    @Override
    public BaseVO delete(Integer id) {
        articleMapper.deleteById(id);
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
        article.setGood(articleUpdateDTO.getGood());
        article.setRecommend(articleUpdateDTO.getRecommend());
        article.setOfficial(articleUpdateDTO.getOfficial());
        articleRepository.save(article);
        return new BaseVO();
    }
}
