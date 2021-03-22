package com.xiyan.controller;

import com.xiyan.dto.*;
import com.xiyan.service.UserService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.UserByIdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author bright
 */
@Api(description = "用户集合")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "发送验证码接口")
    @PostMapping("/send")
    public BaseVO sendOutEmail(@RequestBody @Valid EmailDTO emailDTO) throws MessagingException {
        return userService.sendOutEmail(emailDTO);
    }

    @ApiOperation(value = "注册接口")
    @PostMapping("/register")
    public BaseVO register(@RequestBody @Valid UserDTO userDTO) {
        return userService.register(userDTO);
    }


    @ApiOperation(value = "第三方登陆绑定账号接口")
    @PutMapping("/bind")
    public BaseVO bind(@RequestBody @Valid AccountLoginDTO accountLoginDTO) {
        return userService.bind(accountLoginDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "密码找回接口")
    @PutMapping("/retrieve")
    public BaseVO retrievePassword(@RequestBody @Valid UserDTO userDTO) {
        return userService.retrievePassword(userDTO);
    }

    @ApiOperation(value = "根据参数查询接口")
    @PostMapping("/by")
    public UserByIdVO getUser(@RequestBody GetUserDTO getUserDTO) {
        return userService.getUser(getUserDTO);
    }

    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public BaseVO update(@RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.update(userUpdateDTO);
    }

}
