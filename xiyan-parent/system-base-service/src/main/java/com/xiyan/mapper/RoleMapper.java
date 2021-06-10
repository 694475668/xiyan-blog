package com.xiyan.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiyan.domain.RoleDO;

import java.util.List;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:37
 * @describe :
 */
public interface RoleMapper extends BaseMapper<RoleDO> {
    /**
     * 通过用户ID查询角色信息
     *
     * @param userId
     * @return
     */
    List<RoleDO> queryRoleByUserId(Integer userId);

    /**
     * 添加角色与资源的关系
     *
     * @param roleId
     * @param resourceList
     * @return
     */
    int addResourceRole(Integer roleId, Integer[] resourceList);

    /**
     * 根据角色id查询角色下的绑定的用户
     *
     * @param roleId
     * @return
     */
    Integer hasUser(Integer roleId);

    /**
     * 根据角色id删除资源与角色的关系
     *
     * @param roleId
     * @return
     */
    int delResourceRoleByRoleId(Integer roleId);

}
