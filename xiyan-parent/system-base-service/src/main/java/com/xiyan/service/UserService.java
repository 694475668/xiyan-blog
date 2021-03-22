package com.xiyan.service;


import com.xiyan.dto.*;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.UserByIdVO;

import javax.mail.MessagingException;

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
     * 第三方登陆绑定的账号和密码
     *
     * @param accountLoginDTO
     * @param id
     * @return
     */
    BaseVO bind(AccountLoginDTO accountLoginDTO, Integer id);


    /**
     * 根据参数查询用户
     *
     * @param getUserDTO
     * @return
     */
    UserByIdVO getUser(GetUserDTO getUserDTO);

    /**
     * 修改
     *
     * @param userUpdateDTO
     * @return
     */
    BaseVO update(UserUpdateDTO userUpdateDTO);

    /**
     * 找回密码
     *
     * @param userDTO
     * @return
     */
    BaseVO retrievePassword(UserDTO userDTO);

}
