package com.xiyan.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiyan.domain.ResourceDO;

import java.util.List;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:37
 * @describe :
 */
public interface ResourceMapper extends BaseMapper<ResourceDO> {
    /**
     * 根据角色ID查询所属权限的资源
     *
     * @param roleId
     * @param pidIsNotNull pid是否为空
     * @return
     */
    List<ResourceDO> queryResourceByRoleId(Integer roleId, Boolean pidIsNotNull);

    /**
     * 根据 pid，userId查询
     *
     * @param pid
     * @param userId
     * @return
     */
    List<ResourceDO> queryResource(Integer pid, Integer userId);


    /**
     * 根据pid查询资源
     *
     * @param pid
     * @return
     */
    List<ResourceDO> queryResourceByPid(Integer pid);

    /**
     * 是否有角色拥有当前资源
     *
     * @param id
     * @return
     */
    int hasRoleResource(Integer id);

    /**
     * 根据资源ID删除角色与资源的关系
     *
     * @param id
     * @return
     */
    int delRoleResource(Integer id);
}
