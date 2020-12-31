package com.xiyan.service;


import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.MusicVO;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:38
 * @describe :
 */
public interface MusicService {

    /**
     * 音乐集合
     *
     * @return
     */
    CommonListVO<MusicVO> list();
}
