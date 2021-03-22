package com.xiyan.controller;

import com.xiyan.dto.TechnologySearchDTO;
import com.xiyan.service.TechnologySearchService;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.TechnologySearchVO;
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
@RequestMapping("/technology")
public class TechnologySearchController {

    @Resource
    private TechnologySearchService technologySearchService;


    @ApiOperation(value = "源码集合接口")
    @PostMapping("/list")
    public CommonListVO<TechnologySearchVO> list(@RequestBody @Valid TechnologySearchDTO searchDTO) {
        return technologySearchService.list(searchDTO);
    }
}
