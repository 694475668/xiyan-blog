package com.xiyan.controller;

import com.xiyan.dto.AccountLoginDTO;
import com.xiyan.dto.EmailDTO;
import com.xiyan.dto.UserDTO;
import com.xiyan.service.UserService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.QQRequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


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


    @ApiOperation(value = "登陆接口")
    @PostMapping("/login")
    public BaseVO login(@RequestBody @Valid AccountLoginDTO accountLoginDTO) {
        return userService.login(accountLoginDTO);
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

    @ApiOperation(value = "QQ登陆请求接口")
    @GetMapping("/login")
    public QQRequestVO login() throws UnsupportedEncodingException {
        return userService.login();
    }

    @ApiOperation(value = "QQ回调接口")
    @PostMapping("/qq/back")
    public BaseVO QQBack() throws IOException {
        return userService.QQBack(request.getParameter("code"), request.getParameter("state"));
    }

    @ApiOperation(value = "QQ回调接口")
    @PostMapping("/weibo/back")
    public BaseVO weiboBack() throws IOException {
        return userService.weiboBack(request.getParameter("code"));
    }
}
