package com.xiyan.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiyan.domain.UserDO;

import java.util.List;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:37
 * @describe :
 */
public interface UserMapper extends BaseMapper<UserDO> {
    /**
     * 添加
     *
     * @param userDO
     * @return
     */
    int save(UserDO userDO);

    /**
     * 上传排行榜
     *
     * @return
     */
    List<UserDO> uploadLeaderboard();

    /**
     * 下载排行榜
     *
     * @return
     */
    List<UserDO> downloadLeaderboard();

    /**
     * 收藏排行榜
     *
     * @return
     */
    List<UserDO> favoritesLeaderboard();
}
