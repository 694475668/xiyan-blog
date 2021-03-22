package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.AttentionDO;
import com.xiyan.dto.AttentionAddDTO;
import com.xiyan.dto.AttentionDelDTO;
import com.xiyan.dto.AttentionQueryDTO;
import com.xiyan.dto.GetUserDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.feign.UserFeign;
import com.xiyan.mapper.AttentionMapper;
import com.xiyan.service.AttentionService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.UserByIdVO;
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
    private UserFeign userFeign;

    @Override
    public BaseVO add(AttentionAddDTO attentionAddDTO, Integer id) {
        AttentionDO attentionDO = new AttentionDO();

        UserByIdVO user = userFeign.getUser(new GetUserDTO(null, attentionAddDTO.getUsername()));
        attentionDO.setUserId(id);
        attentionDO.setAttentionUserId(user.getId());
        attentionDO.setCreateTime(new Date());
        attentionMapper.insert(attentionDO);
        return new BaseVO();
    }

    @Override
    public BaseVO delete(AttentionDelDTO attentionDelDTO, Integer id) {
        UserByIdVO user = userFeign.getUser(new GetUserDTO(null, attentionDelDTO.getUsername()));
        attentionMapper.delete(new QueryWrapper<AttentionDO>().and(attentionDOQueryWrapper -> attentionDOQueryWrapper.eq("user_id", id).eq("attention_user_id", user.getId())));
        return new BaseVO();
    }

    @Override
    public BaseVO query(AttentionQueryDTO attentionQueryDTO, Integer id) {
        UserByIdVO user = userFeign.getUser(new GetUserDTO(null, attentionQueryDTO.getUsername()));
        AttentionDO attentionDO = attentionMapper.selectOne(new QueryWrapper<AttentionDO>().and(attentionDOQueryWrapper -> attentionDOQueryWrapper.eq("user_id", id).eq("attention_user_id", user.getId())));
        if (null == attentionDO) {
            return new BaseVO(true, ErrorCodeEnum.E0704.getKey(), ErrorCodeEnum.E0704.getValue());
        }
        return new BaseVO(true, ErrorCodeEnum.E0703.getKey(), ErrorCodeEnum.E0703.getValue());
    }
}
