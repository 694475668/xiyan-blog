package com.xiyan.controller;

import com.xiyan.dto.RoleAddDTO;
import com.xiyan.dto.RoleUpdateDTO;
import com.xiyan.dto.RoleVagueDTO;
import com.xiyan.service.RoleService;
import com.xiyan.vo.*;
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
@Api(tags = "角色集合")
@RequestMapping("/role")
@RestController
public class RoleController {
    @Resource
    private RoleService roleService;

    @Resource
    private HttpServletRequest request;


    @ApiOperation(value = "用户界面角色列表")
    @GetMapping(value = "/list")
    public BaseVO list() {
        return roleService.list(Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "根据用户id查询所属角色列表接口")
    @GetMapping(value = "/by/user/{id}")
    public CommonListVO<RoleVO> queryRolesByUserId(@PathVariable("id") Integer id) {
        return roleService.queryRolesByUserId(id);
    }

    @PostMapping("/list")
    @ApiOperation(value = "角色列表接口")
    @ApiImplicitParam(name = "roleVagueDTO", dataType = "RoleVagueDTO")
    public CommonListVO<RoleListVO> list(@RequestBody @Valid RoleVagueDTO roleVagueDTO) {
        return roleService.list(roleVagueDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @PostMapping("/add")
    @ApiOperation("添加接口")
    @ApiImplicitParam(name = "roleAddDTO", dataType = "RoleAddDTO")
    public BaseVO add(@RequestBody @Valid RoleAddDTO roleAddDTO) {
        return roleService.add(roleAddDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除接口")
    @ApiImplicitParam(name = "id", value = "角色ID")
    public BaseVO delete(@PathVariable Integer id) {
        return roleService.delete(id);
    }

    @ApiOperation(value = "根据角色id查询")
    @GetMapping(value = "/by/{id}")
    public RoleByIdVO queryRolesById(@PathVariable("id") Integer id) {
        return roleService.queryRoleById(id);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "修改接口")
    @ApiImplicitParam(name = "id", value = "角色ID")
    public BaseVO update(@PathVariable Integer id, @RequestBody @Valid RoleUpdateDTO roleUpdateDTO) {
        return roleService.update(roleUpdateDTO, id, request.getHeader("User-NAME"));
    }
}
