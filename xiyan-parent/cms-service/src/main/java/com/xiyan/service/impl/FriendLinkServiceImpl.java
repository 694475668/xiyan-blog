package com.xiyan.service.impl;

import com.xiyan.domain.FriendLinkDO;
import com.xiyan.mapper.FriendLinkMapper;
import com.xiyan.service.FriendLinkService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.FriendLinkVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/1 16:19
 */
@Service
public class FriendLinkServiceImpl implements FriendLinkService {
    @Resource
    private FriendLinkMapper friendLinkMapper;

    @Override
    @Cacheable(cacheNames = "friendLink", key = "#root.caches[0].name")
    public CommonListVO<FriendLinkVO> list() {
        CommonListVO commonListVO = new CommonListVO();
        List<FriendLinkDO> friendLinkList = friendLinkMapper.selectList(null);
        List<FriendLinkVO> friendLinkVOList = ObjectConvertUtil.convertInstance().objectConvert(friendLinkList, FriendLinkVO.class);
        commonListVO.setVoList(friendLinkVOList);
        return commonListVO;
    }
}
