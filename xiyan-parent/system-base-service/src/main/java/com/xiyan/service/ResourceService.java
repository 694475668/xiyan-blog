package com.xiyan.service;

import com.xiyan.dto.ResourceAddDTO;
import com.xiyan.dto.ResourceByIdVO;
import com.xiyan.dto.ResourceUpdateDTO;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.ResourceTreeVO;

/**
 * @author: 【 bright 】
 * @date: 【 2021/3/27 0027 12:36 】
 * @Description :
 */
public interface ResourceService {
    /**
     * 根据用户id获取所属资源列表
     *
     * @param pid
     * @param userId
     * @return
     */
    CommonListVO<ResourceTreeVO> queryResourceByUserId(Integer pid, Integer userId);


    /**
     * 资源权限列表
     *
     * @param pid
     * @return
     */
    CommonListVO<ResourceTreeVO> list(Integer pid);


    /**
     * 添加
     *
     * @param resourceAddDTO
     * @param username
     * @return
     */
    BaseVO add(ResourceAddDTO resourceAddDTO, String username);

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    BaseVO delete(Integer id);

    /**
     * 修改
     *
     * @param resourceUpdateDTO
     * @param id
     * @param username
     * @return
     */
    BaseVO update(ResourceUpdateDTO resourceUpdateDTO, Integer id, String username);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ResourceByIdVO queryResourceById(Integer id);
}
