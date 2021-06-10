package com.xiyan.service;

import com.xiyan.vo.BaseVO;

import java.io.IOException;

/**
 * @author : bright
 * @Description :
 * @date:Created in 2021/1/29 0029 21:52
 */
public interface WeiBoService {

    /**
     * 微博回调
     *
     * @param code
     * @return
     * @throws IOException
     */
    BaseVO weiboBack(String code) throws IOException;
}
