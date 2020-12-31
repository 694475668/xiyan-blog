package com.xiyan.controller;

import com.xiyan.dto.*;
import com.xiyan.service.CommentService;
import com.xiyan.service.FavoritesService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommentVO;
import com.xiyan.vo.CommonListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author: bright
 * @date:Created in 2020/12/13 13:40
 * @describe :
 */
@Api(description = "收藏集合")
@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    @Resource
    private FavoritesService favoritesService;

    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "添加接口")
    @PostMapping("/add")
    public BaseVO add(@RequestBody @Valid FavoritesAddDTO favoritesAddDTO) {
        return favoritesService.add(favoritesAddDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "删除接口")
    @PostMapping("/delete")
    public BaseVO delete(@RequestBody @Valid FavoritesDelDTO favoritesDelDTO) {
        return favoritesService.delete(favoritesDelDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "查询接口")
    @PostMapping("/query")
    public BaseVO query(@RequestBody @Valid FavoritesQueryDTO favoritesQueryDTO) {
        return favoritesService.query(favoritesQueryDTO, Integer.valueOf(request.getHeader("User-ID")));
    }
}
