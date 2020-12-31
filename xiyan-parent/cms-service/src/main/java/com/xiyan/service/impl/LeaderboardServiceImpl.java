package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.UserDO;
import com.xiyan.mapper.UserMapper;
import com.xiyan.service.LeaderboardService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.LeaderboardVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/2 15:34
 */
@Service
public class LeaderboardServiceImpl implements LeaderboardService {
    @Resource
    private UserMapper userMapper;

    @Override
    public CommonListVO<LeaderboardVO> gold() {
        CommonListVO commonListVO = new CommonListVO();
        LeaderboardVO leaderboardVO = null;
        List<LeaderboardVO> leaderboardVOList = new ArrayList<>();
        List<UserDO> user = userMapper.selectList(new QueryWrapper<UserDO>().orderByDesc("point"));
        for (UserDO userDO : user) {
            leaderboardVO = new LeaderboardVO();
            BeanUtils.copyProperties(userDO, leaderboardVO);
            leaderboardVO.setCount(userDO.getPoint());
            leaderboardVOList.add(leaderboardVO);
        }
        commonListVO.setVoList(leaderboardVOList);
        return commonListVO;
    }

    @Override
    public CommonListVO<LeaderboardVO> upload() {
        CommonListVO commonListVO = new CommonListVO();
        List<UserDO> user = userMapper.uploadLeaderboard();
        List<LeaderboardVO> leaderboardVOList = ObjectConvertUtil.convertInstance().objectConvert(user, LeaderboardVO.class);
        commonListVO.setVoList(leaderboardVOList);
        return commonListVO;
    }

    @Override
    public CommonListVO<LeaderboardVO> download() {
        CommonListVO commonListVO = new CommonListVO();
        List<UserDO> user = userMapper.downloadLeaderboard();
        List<LeaderboardVO> leaderboardVOList = ObjectConvertUtil.convertInstance().objectConvert(user, LeaderboardVO.class);
        commonListVO.setVoList(leaderboardVOList);
        return commonListVO;
    }

    @Override
    public CommonListVO<LeaderboardVO> favorites() {
        CommonListVO commonListVO = new CommonListVO();
        List<UserDO> user = userMapper.favoritesLeaderboard();
        List<LeaderboardVO> leaderboardVOList = ObjectConvertUtil.convertInstance().objectConvert(user, LeaderboardVO.class);
        commonListVO.setVoList(leaderboardVOList);
        return commonListVO;
    }
}
