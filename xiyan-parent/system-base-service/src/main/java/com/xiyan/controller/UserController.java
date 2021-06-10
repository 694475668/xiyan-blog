package com.xiyan.controller;

import com.xiyan.dto.*;
import com.xiyan.service.UserService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.UserByIdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author: 【 bright 】
 * @date: 【 2021/3/24 0024 15:16 】
 * @Description :
 */
@Api(tags = "用户集合")
@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private HttpServletRequest request;


    @ApiOperation(value = "用户列表")
    @PostMapping(value = "/list")
    public BaseVO getAccountToken(@RequestBody @Validated UserVagueDTO userVagueDTO) {
        return userService.list(userVagueDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "根据用户名获取密码接口，内部使用，不对外暴露")
    @GetMapping(value = "/by/username/{username}")
    public BaseVO getUserByUserName(@PathVariable("username") String username) {
        return userService.getUserByUserName(username);
    }

    @PostMapping("/add")
    @ApiOperation("添加接口")
    @ApiImplicitParam(name = "userAddDTO", dataType = "UserAddDTO")
    public BaseVO add(@RequestBody @Valid UserAddDTO userAddDTO) {
        return userService.add(userAddDTO, Integer.valueOf(request.getHeader("User-ID")), request.getHeader("User-NAME"));
    }

    @GetMapping("/by/{id}")
    @ApiOperation("根据用户ID查询用户信息接口")
    @ApiImplicitParam(name = "id", value = "用户ID")
    public UserByIdVO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update/{id}")
    @ApiOperation("修改接口")
    public BaseVO updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO, @PathVariable Integer id) {
        return userService.update(userUpdateDTO, id, request.getHeader("User-NAME"));
    }

    @PostMapping("/operating")
    @ApiOperation("加解锁接口")
    public BaseVO unlockAndLock(@RequestBody @Valid UnlockAndLockDTO unlockAndLockDTO) {
        return userService.unlockAndLock(unlockAndLockDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除接口")
    public BaseVO delete(@PathVariable("id") Integer id) {
        return userService.delete(id, Integer.valueOf(request.getHeader("User-ID")));
    }

    @PutMapping("/update/pwd")
    @ApiOperation("修改密码接口")
    public BaseVO updatePwd(@RequestBody @Valid UserPwdDTO userPwdDTO) {
        return userService.updatePwd(userPwdDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @PostMapping("/authorization")
    @ApiOperation(value = "授权接口")
    @ApiImplicitParam(name = "userGrantDTO", dataType = "UserGrantDTO")
    public BaseVO grant(@RequestBody @Valid UserGrantDTO userGrantDTO) {
        return userService.authorization(userGrantDTO);
    }
}
