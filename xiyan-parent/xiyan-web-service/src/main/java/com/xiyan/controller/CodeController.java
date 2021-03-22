package com.xiyan.controller;

import com.xiyan.dto.*;
import com.xiyan.service.CodeService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CodeVO;
import com.xiyan.vo.CommonListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author bright
 */
@Api(description = "源码集合")
@RestController
@RequestMapping("/code")
public class CodeController {

    @Resource
    private CodeService codeService;

    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "标签强集合接口")
    @GetMapping("/tag/list")
    public CommonListVO<CodeVO> tagList() {
        return codeService.tagList();
    }


    @ApiOperation(value = "源码集合接口")
    @PostMapping("/list")
    public CommonListVO<CodeVO> list(@RequestBody @Valid SortDTO sortDTO) {
        return codeService.list(sortDTO);
    }

    @ApiOperation(value = "根据id查询源码信息接口")
    @GetMapping("/byId/{id}")
    public BaseVO getCodeById(@PathVariable("id") Integer id) {
        return codeService.getCodeById(id);
    }

    @ApiOperation(value = "浏览接口")
    @GetMapping("/browse/{id}")
    public BaseVO browse(@PathVariable("id") Integer id) {
        return codeService.browse(id);
    }

    @ApiOperation(value = "点赞接口")
    @GetMapping("/like/{id}")
    public BaseVO like(@PathVariable("id") Integer id) {
        return codeService.like(id);
    }


    @ApiOperation(value = "添加接口")
    @PostMapping("/add")
    public BaseVO add(@RequestBody @Valid CodeAddDTO codeAddDTO) {
        return codeService.add(codeAddDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "修改接口")
    @PutMapping("/update/{id}")
    public BaseVO update(@RequestBody @Valid CodeUpdateDTO codeUpdateDTO, @PathVariable("id") Integer id) {
        return codeService.update(codeUpdateDTO, id);
    }

    @ApiOperation(value = "下载接口")
    @PostMapping("/download")
    public BaseVO download(@RequestBody @Valid CodeDownloadDTO codeDownloadDTO) {
        return codeService.download(codeDownloadDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "审核接口")
    @PutMapping("/review/{id}")
    public BaseVO review(@RequestBody CodeReviewDTO codeReviewDTO, @PathVariable("id") Integer id) {
        return codeService.review(codeReviewDTO, id);
    }
}
