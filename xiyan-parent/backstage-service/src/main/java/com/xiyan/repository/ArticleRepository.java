package com.xiyan.repository;

import com.xiyan.bo.ArticleBO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/24 11:30
 */
public interface ArticleRepository extends ElasticsearchRepository<ArticleBO, Integer> {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ArticleBO findArticleById(Integer id);


}
