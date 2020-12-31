package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.AttentionDO;
import com.xiyan.domain.UserDO;
import com.xiyan.dto.AttentionAddDTO;
import com.xiyan.dto.AttentionDelDTO;
import com.xiyan.dto.AttentionQueryDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.AttentionMapper;
import com.xiyan.mapper.UserMapper;
import com.xiyan.service.AttentionService;
import com.xiyan.vo.BaseVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/16 14:09
 */
@Service
public class AttentionServiceImpl implements AttentionService {

    @Resource
    private AttentionMapper attentionMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public BaseVO add(AttentionAddDTO attentionAddDTO, Integer id) {
        AttentionDO attentionDO = new AttentionDO();
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", attentionAddDTO.getUsername()));
        attentionDO.setUserId(id);
        attentionDO.setAttentionUserId(userDO.getId());
        attentionDO.setCreateTime(new Date());
        attentionMapper.insert(attentionDO);
        return new BaseVO();
    }

    @Override
    public BaseVO delete(AttentionDelDTO attentionDelDTO, Integer id) {
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", attentionDelDTO.getUsername()));
        attentionMapper.delete(new QueryWrapper<AttentionDO>().and(attentionDOQueryWrapper -> attentionDOQueryWrapper.eq("user_id", id).eq("attention_user_id", userDO.getId())));
        return new BaseVO();
    }

    @Override
    public BaseVO query(AttentionQueryDTO attentionQueryDTO, Integer id) {
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", attentionQueryDTO.getUsername()));
        AttentionDO attentionDO = attentionMapper.selectOne(new QueryWrapper<AttentionDO>().and(attentionDOQueryWrapper -> attentionDOQueryWrapper.eq("user_id", id).eq("attention_user_id", userDO.getId())));
        if (null == attentionDO) {
            return new BaseVO(true, ErrorCodeEnum.E0711.getKey(), ErrorCodeEnum.E0711.getValue());
        }
        return new BaseVO(true, ErrorCodeEnum.E0710.getKey(), ErrorCodeEnum.E0710.getValue());
    }
}
