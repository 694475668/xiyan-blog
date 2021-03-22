package com.xiyan.repository;

import com.xiyan.bo.CodeBO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/24 11:30
 */
public interface CodeRepository extends ElasticsearchRepository<CodeBO, Integer> {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CodeBO findArticleById(Integer id);
}
