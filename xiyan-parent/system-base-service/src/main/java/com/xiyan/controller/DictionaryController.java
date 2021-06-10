package com.xiyan.controller;


import com.xiyan.dto.DictionaryByNameDTO;
import com.xiyan.dto.DictionaryDTO;
import com.xiyan.dto.DictionaryVagueDTO;
import com.xiyan.service.DictionaryService;
import com.xiyan.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author bright
 */
@Api(description = "数据字典集合")
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "根据数据名称查询接口")
    @PostMapping("/by/name")
    public CommonListVO<DictionaryVO> list(@RequestBody @Valid DictionaryByNameDTO dictionaryByNameDTO) {
        return dictionaryService.list(dictionaryByNameDTO);
    }

    @ApiOperation(value = "集合数据列表")
    @PostMapping("/list")
    @ApiImplicitParam(name = "dictionaryVagueDTO", dataType = "DictionaryVagueDTO")
    public CommonListVO<DictionaryVagueVO> query(@RequestBody @Valid DictionaryVagueDTO dictionaryVagueDTO) {
        return dictionaryService.query(dictionaryVagueDTO);
    }

    @ApiOperation(value = "添加接口")
    @PostMapping("/add")
    @ApiImplicitParam(name = "dictionaryDTO", dataType = "DictionaryDTO")
    public BaseVO add(@RequestBody @Valid DictionaryDTO dictionaryDTO) {
        return dictionaryService.add(dictionaryDTO, request.getHeader("User-NAME"));
    }

    @ApiOperation(value = "修改接口")
    @PutMapping("/update/{id}")
    @ApiImplicitParam(name = "id", value = "字典ID")
    public BaseVO update(@RequestBody @Valid DictionaryDTO dictionaryDTO, @PathVariable Integer id) {
        return dictionaryService.update(dictionaryDTO, id, request.getHeader("User-NAME"));
    }

    @ApiOperation(value = "删除接口")
    @DeleteMapping("/delete/{id}")
    @ApiImplicitParam(name = "id", value = "字典ID")
    public BaseVO delete(@PathVariable("id") Integer id) {
        return dictionaryService.delete(id);
    }

    @ApiOperation(value = "根据id查询数据字典信息接口")
    @GetMapping("/by/{id}")
    @ApiImplicitParam(name = "id", value = "字典ID")
    public DictionaryByIdVO queryDictionaryById(@PathVariable Integer id) {
        return dictionaryService.queryDictionaryById(id);
    }
}
