package com.xiyan.service;

import com.xiyan.dto.ArticleReviewDTO;
import com.xiyan.dto.ArticleUpdateDTO;
import com.xiyan.dto.SortDTO;
import com.xiyan.vo.ArticleVO;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;

/**
 * @author: bright
 * @date:Created in 2020/11/22 18:28
 * @describe :
 */
public interface ArticleService {
    /**
     * 审核
     *
     * @param articleReviewDTO
     * @param id
     * @return
     */
    BaseVO review(ArticleReviewDTO articleReviewDTO, Integer id);

    /**
     * 博客列表
     *
     * @param sortDTO
     * @return
     */
    CommonListVO<ArticleVO> list(SortDTO sortDTO);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    BaseVO delete(Integer id);

    /**
     * 修改
     *
     * @param articleUpdateDTO
     * @param id
     * @return
     */
    BaseVO update(ArticleUpdateDTO articleUpdateDTO, Integer id);
}
