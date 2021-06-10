package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyan.domain.TalkDO;
import com.xiyan.dto.GetUserDTO;
import com.xiyan.dto.TalkAddDTO;
import com.xiyan.dto.TalkFindDTO;
import com.xiyan.dto.TalkUpdateDTO;
import com.xiyan.feign.UserFeign;
import com.xiyan.mapper.TalkMapper;
import com.xiyan.service.TalkService;
import com.xiyan.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/1 13:43
 */
@Service
@Slf4j
public class TalkServiceImpl implements TalkService {
    @Resource
    private TalkMapper talkMapper;


    @Resource
    private UserFeign userFeign;


    @Override
    public CommonListVO<TalkListVO> list(TalkFindDTO talkFindDTO) {
        CommonListVO<TalkListVO> commonListVO = new CommonListVO<>();
        Page<TalkDO> talkDOPage = new Page<>(talkFindDTO.getPageNo(), talkFindDTO.getPageSize());
        QueryWrapper<TalkDO> talkDOQueryWrapper = new QueryWrapper<TalkDO>().and(queryWrapper -> queryWrapper.eq("type", talkFindDTO.getType()));
        //查询
        talkDOPage = talkMapper.selectPage(talkDOPage, talkDOQueryWrapper);
        //获取查询的数据
        List<TalkDO> talkDOList = talkDOPage.getRecords();
        //总条数
        commonListVO.setTotal(talkDOPage.getTotal());
        List<TalkListVO> talkListList = new ArrayList<>();
        TalkListVO talkListVO = null;
        for (TalkDO talkDO : talkDOList) {
            talkListVO = new TalkListVO();
            BeanUtils.copyProperties(talkDO, talkListVO);
            UserByIdVO user = userFeign.getUser(new GetUserDTO(talkDO.getUserId(), null));
            talkListVO.setName(user.getName());
            talkListVO.setPhoto(user.getPhoto());
            talkListList.add(talkListVO);
        }
        commonListVO.setVoList(talkListList);
        return commonListVO;
    }

    @Override
    public BaseVO getTalkById(Integer id) {
        TalkByIdVO talkByIdVO = new TalkByIdVO();
        TalkDO talkDO = talkMapper.selectById(id);
        BeanUtils.copyProperties(talkDO, talkByIdVO);
        return talkByIdVO;
    }

    @Override
    public BaseVO add(TalkAddDTO talkAddDTO, Integer userId) {
        TalkDO talkDO = new TalkDO();
        BeanUtils.copyProperties(talkAddDTO, talkDO);
        talkDO.setUserId(userId);
        talkDO.setCreateTime(new Date());
        talkDO.setUpdateTime(new Date());
        talkMapper.insert(talkDO);
        return new BaseVO();
    }

    @Override
    public BaseVO browse(Integer id) {
        talkMapper.browse(id);
        return new BaseVO();
    }

    @Override
    public BaseVO update(TalkUpdateDTO talkUpdateDTO, Integer id) {
        TalkDO talkDO = new TalkDO();
        BeanUtils.copyProperties(talkUpdateDTO, talkDO);
        talkDO.setCreateTime(new Date());
        talkDO.setUpdateTime(new Date());
        talkDO.setId(id);
        talkMapper.updateById(talkDO);
        return new BaseVO();
    }
}
