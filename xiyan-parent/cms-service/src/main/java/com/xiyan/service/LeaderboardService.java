package com.xiyan.service;

import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.LeaderboardVO;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/2 15:26
 */
public interface LeaderboardService {
    /**
     * 金币排行榜
     *
     * @return
     */
    CommonListVO<LeaderboardVO> gold();

    /**
     * 上传排行榜
     *
     * @return
     */
    CommonListVO<LeaderboardVO> upload();

    /**
     * 下载排行榜
     *
     * @return
     */
    CommonListVO<LeaderboardVO> download();

    /**
     * 收藏排行榜
     *
     * @return
     */
    CommonListVO<LeaderboardVO> favorites();


}
