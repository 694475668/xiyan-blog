package com.xiyan.service;

import com.xiyan.dto.FavoritesAddDTO;
import com.xiyan.dto.FavoritesDelDTO;
import com.xiyan.dto.FavoritesQueryDTO;
import com.xiyan.vo.BaseVO;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/15 14:38
 */
public interface FavoritesService {

    /**
     * 添加
     *
     * @param favoritesAddDTO
     * @param id
     * @return
     */
    BaseVO add(FavoritesAddDTO favoritesAddDTO, Integer id);

    /**
     * 删除
     *
     * @param favoritesDelDTO
     * @param id
     * @return
     */
    BaseVO delete(FavoritesDelDTO favoritesDelDTO, Integer id);

    /**
     * 查询文章是否被收藏
     *
     * @param favoritesQueryDTO
     * @param id
     * @return
     */
    BaseVO query(FavoritesQueryDTO favoritesQueryDTO, Integer id);
}
