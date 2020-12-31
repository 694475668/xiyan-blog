package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.FavoritesDO;
import com.xiyan.dto.FavoritesAddDTO;
import com.xiyan.dto.FavoritesDelDTO;
import com.xiyan.dto.FavoritesQueryDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.FavoritesMapper;
import com.xiyan.service.FavoritesService;
import com.xiyan.vo.BaseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/15 14:38
 */
@Service
public class FavoritesServiceImpl implements FavoritesService {
    @Resource
    private FavoritesMapper favoritesMapper;

    @Override
    public BaseVO add(FavoritesAddDTO favoritesAddDTO, Integer id) {
        FavoritesDO favoritesDO = new FavoritesDO();
        BeanUtils.copyProperties(favoritesAddDTO, favoritesDO);
        favoritesDO.setCreateTime(new Date());
        favoritesDO.setUserId(id);
        favoritesMapper.insert(favoritesDO);
        return new BaseVO();
    }

    @Override
    public BaseVO delete(FavoritesDelDTO favoritesDelDTO, Integer id) {
        favoritesMapper.delete(new QueryWrapper<FavoritesDO>().and(favoritesDOQueryWrapper -> favoritesDOQueryWrapper
                .eq("user_id", id).eq("info_id", favoritesDelDTO.getInfoId()).eq("type", favoritesDelDTO.getType())));
        return new BaseVO();
    }

    @Override
    public BaseVO query(FavoritesQueryDTO favoritesQueryDTO, Integer id) {
        FavoritesDO favoritesDO = favoritesMapper.selectOne(new QueryWrapper<FavoritesDO>().and(favoritesDOQueryWrapper -> favoritesDOQueryWrapper
                .eq("user_id", id).eq("info_id", favoritesQueryDTO.getInfoId()).eq("type", favoritesQueryDTO.getType())));
        if (null == favoritesDO) {
            return new BaseVO(true, ErrorCodeEnum.E0709.getKey(), ErrorCodeEnum.E0709.getValue());
        }
        return new BaseVO(true, ErrorCodeEnum.E0708.getKey(), ErrorCodeEnum.E0708.getValue());
    }
}
