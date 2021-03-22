package com.xiyan.service;

import com.xiyan.vo.BaseVO;
import com.xiyan.vo.QQRequestVO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author : bright
 * @Description :
 * @date:Created in 2021/1/29 0029 21:51
 */
public interface QQService {
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
}
