package com.xiyan.controller;

import com.xiyan.dto.ResourceAddDTO;
import com.xiyan.dto.ResourceByIdVO;
import com.xiyan.dto.ResourceUpdateDTO;
import com.xiyan.service.ResourceService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.ResourceTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author: 【 bright 】
 * @date: 【 2021/3/24 0024 15:16 】
 * @Description :
 */
@Api(tags = "资源集合")
@RequestMapping("/resource")
@RestController
public class ResourceController {
    @Resource
    private ResourceService resourceService;

    @Resource
    private HttpServletRequest request;


    @ApiOperation(value = "用户所属资源列表")
    @GetMapping(value = "/by/list")
    public CommonListVO<ResourceTreeVO> list() {
        return resourceService.queryResourceByUserId(null, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "资源列表接口")
    @GetMapping(value = "/list")
    public CommonListVO<ResourceTreeVO> treeList() {
        return resourceService.list(null);
    }

    @ApiOperation(value = "添加资源接口")
    @PostMapping(value = "/add")
    @ApiImplicitParam(name = "resourceAddDTO", dataType = "ResourceAddDTO")
    public BaseVO add(@RequestBody @Valid ResourceAddDTO resourceAddDTO) {
        return resourceService.add(resourceAddDTO, request.getHeader("User-NAME"));
    }

    @ApiOperation(value = "删除资源接口")
    @ApiImplicitParam(name = "id", value = "资源ID")
    @DeleteMapping("/delete/{id}")
    public BaseVO delete(@PathVariable(value = "id") Integer id) {
        return resourceService.delete(id);
    }

    @ApiOperation(value = "修改资源接口")
    @PutMapping("/update/{id}")
    @ApiImplicitParam(name = "id", value = "资源ID")
    public BaseVO update(@RequestBody @Valid ResourceUpdateDTO resourceUpdateDTO, @PathVariable Integer id) {
        return resourceService.update(resourceUpdateDTO, id, request.getHeader("User-NAME"));
    }

    @ApiOperation(value = "根据ID查询接口")
    @GetMapping(value = "/by/{id}")
    @ApiImplicitParam(name = "id", value = "资源ID")
    public ResourceByIdVO queryResourceById(@PathVariable(value = "id") Integer id) {
        return resourceService.queryResourceById(id);
    }
}
