package com.xiyan.service;

import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.FriendLinkVO;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/1 16:18
 */
public interface FriendLinkService {
    /**
     * 友链列表
     *
     * @return
     */
    CommonListVO<FriendLinkVO> list();
}
