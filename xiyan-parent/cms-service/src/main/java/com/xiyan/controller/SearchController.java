package com.xiyan.controller;

import com.xiyan.dto.SearchDTO;
import com.xiyan.service.SearchService;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.SearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * @author bright
 */
@Api(description = "搜索集合")
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;


    @ApiOperation(value = "源码集合接口")
    @PostMapping("/list")
    public CommonListVO<SearchVO> list(@RequestBody @Valid SearchDTO searchDTO) {
        return searchService.list(searchDTO);
    }
}
