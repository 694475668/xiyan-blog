package com.xiyan.service.impl;

import com.alibaba.fastjson.JSON;
import com.xiyan.domain.UserDO;
import com.xiyan.dto.SearchDTO;
import com.xiyan.mapper.UserMapper;
import com.xiyan.service.SearchService;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.SearchVO;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/25 11:16
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private UserMapper userMapper;


    @Override
    public CommonListVO<SearchVO> list(SearchDTO searchDTO) {
        CommonListVO commonListVO = new CommonListVO();
        //分页
        Pageable pageable = PageRequest.of(searchDTO.getPageNo() - 1, searchDTO.getPageSize());
        //排序
        SortBuilder<FieldSortBuilder> sortBuilder = new FieldSortBuilder("createTime").order(SortOrder.DESC);
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                //构建模糊查询多条件,boost即为权重，数值越大，权重越大
                .withQuery(QueryBuilders.boolQuery().should(QueryBuilders.fuzzyQuery("tag", searchDTO.getKeywords()).boost(1))
                        .should(QueryBuilders.fuzzyQuery("remark", searchDTO.getKeywords()).boost(1))
                        .should(QueryBuilders.matchQuery("title", searchDTO.getKeywords()).boost(3)))
                .withPageable(pageable)
                .withSort(sortBuilder)
                .withHighlightFields(new HighlightBuilder.Field("title")
                        .preTags("<font style='color:red;font-weight:bold'>")
                        .postTags("</font>"))
                .build();
        //IndexCoordinates多个索引组合查询，不加是单个，根据class进行查找索引
        SearchHits<SearchVO> searchHits = elasticsearchRestTemplate.search(query, SearchVO.class, IndexCoordinates.of("code", "article"));
        List<SearchHit<SearchVO>> searchHitList = searchHits.getSearchHits();
        //总条数
        commonListVO.setTotal(searchHits.getTotalHits());
        List<SearchVO> searchVOList = new ArrayList<>();
        List<String> tagList = null;
        SearchVO searchVO = null;
        for (SearchHit h : searchHitList) {
            List<String> highlightField = h.getHighlightField("title");
            String nameValue = highlightField.get(0);
            SearchVO search = JSON.parseObject(JSON.toJSONString(h.getContent()), SearchVO.class);
            search.setTitle(nameValue);
            searchVO = new SearchVO();
            UserDO user = userMapper.selectById(search.getUserId());
            BeanUtils.copyProperties(search, searchVO);
            searchVO.setName(user.getName());
            //标签拆分
            String[] split = search.getTag().split(",");
            //数组转换集合
            tagList = Arrays.asList(split);
            searchVO.setTagList(tagList);
            searchVOList.add(searchVO);
            commonListVO.setVoList(searchVOList);
        }
        return commonListVO;
    }
}
