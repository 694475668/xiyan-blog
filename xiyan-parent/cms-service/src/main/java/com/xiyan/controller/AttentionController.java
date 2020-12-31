package com.xiyan.controller;

import com.xiyan.dto.*;
import com.xiyan.service.ArticleService;
import com.xiyan.service.AttentionService;
import com.xiyan.vo.ArticleVO;
import com.xiyan.vo.BaseVO;
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
@Api(description = "关注集合")
@RestController
@RequestMapping("/attention")
public class AttentionController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private AttentionService attentionService;


    @ApiOperation(value = "添加接口")
    @PostMapping("/add")
    public BaseVO add(@RequestBody @Valid AttentionAddDTO attentionAddDTO) {
        return attentionService.add(attentionAddDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "查询接口")
    @PostMapping("/query")
    public BaseVO query(@RequestBody @Valid AttentionQueryDTO attentionQueryDTO) {
        return attentionService.query(attentionQueryDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "删除接口")
    @PostMapping("/delete")
    public BaseVO delete(@RequestBody @Valid AttentionDelDTO attentionDelDTO) {
        return attentionService.delete(attentionDelDTO, Integer.valueOf(request.getHeader("User-ID")));
    }
}
