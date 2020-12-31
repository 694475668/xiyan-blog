package com.xiyan.service;


import com.xiyan.dto.AccountLoginDTO;
import com.xiyan.dto.EmailDTO;
import com.xiyan.dto.UserDTO;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.QQRequestVO;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:38
 * @describe :
 */
public interface UserService {
    /**
     * 发送验证码
     *
     * @param emailDTO
     * @return
     * @throws MessagingException
     */
    BaseVO sendOutEmail(EmailDTO emailDTO) throws MessagingException;

    /**
     * 注册
     *
     * @param userDTO
     * @return
     */
    BaseVO register(UserDTO userDTO);


    /**
     * 登陆
     *
     * @param accountLoginDTO
     * @return
     */
    BaseVO login(AccountLoginDTO accountLoginDTO);


    /**
     * 第三方登陆绑定的账号和密码
     *
     * @param accountLoginDTO
     * @param id
     * @return
     */
    BaseVO bind(AccountLoginDTO accountLoginDTO, Integer id);


    /**
     * 找回密码
     *
     * @param userDTO
     * @return
     */
    BaseVO retrievePassword(UserDTO userDTO);


    /**
     * QQ登陆请求地址
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    QQRequestVO login() throws UnsupportedEncodingException;


    /**
     * QQ回调
     *
     * @param code
     * @param state
     * @return
     * @throws IOException
     */
    BaseVO QQBack(String code, String state) throws IOException;

    /**
     * 微博回调
     *
     * @param code
     * @return
     * @throws IOException
     */
    BaseVO weiboBack(String code) throws IOException;


}
