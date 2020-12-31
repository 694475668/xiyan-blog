package com.xiyan.service;

import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.TvVO;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/17 16:29
 */
public interface TvService {
    /**
     * 电视列表
     *
     * @return
     */
    CommonListVO<TvVO> list();
}
