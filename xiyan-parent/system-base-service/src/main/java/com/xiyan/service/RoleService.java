package com.xiyan.service;

import com.xiyan.dto.RoleAddDTO;
import com.xiyan.dto.RoleUpdateDTO;
import com.xiyan.dto.RoleVagueDTO;
import com.xiyan.vo.*;

/**
 * @author: 【 bright 】
 * @date: 【 2021/3/26 0026 10:22 】
 * @Description :
 */
public interface RoleService {

    /**
     * 用户界面的角色列表
     *
     * @param userId
     * @return
     */
    CommonListVO<RoleVO> list(Integer userId);


    /**
     * 根据用户id查询所属角色列表
     *
     * @param id
     * @return
     */
    CommonListVO<RoleVO> queryRolesByUserId(Integer id);

    /**
     * 角色列表
     *
     * @param roleVagueDTO
     * @param userId
     * @return
     */
    CommonListVO<RoleListVO> list(RoleVagueDTO roleVagueDTO, Integer userId);

    /**
     * 添加角色
     *
     * @param roleAddDTO
     * @param userId
     * @return
     */
    BaseVO add(RoleAddDTO roleAddDTO, Integer userId);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    BaseVO delete(Integer id);

    /**
     * 根据角色ID查询角色信息
     *
     * @param id
     * @return
     */
    RoleByIdVO queryRoleById(Integer id);

    /**
     * 修改
     *
     * @param roleUpdateDTO
     * @param id
     * @param username
     * @return
     */
    BaseVO update(RoleUpdateDTO roleUpdateDTO, Integer id, String username);
}
