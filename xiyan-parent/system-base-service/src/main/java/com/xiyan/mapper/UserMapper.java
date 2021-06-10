package com.xiyan.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiyan.domain.UserDO;

import java.util.List;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:37
 * @describe :
 */
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 根据pid查询
     *
     * @param pid
     * @return
     */
    List<UserDO> queryUserByPid(Integer pid);


    /**
     * 根据用户id删除用户与角色的关系
     *
     * @param userId
     * @return
     */
    int delUserRoleByUserId(Integer userId);


    /**
     * 添加角色与用户的关系
     *
     * @param userId
     * @param roleList
     * @return
     */
    int addUserRole(Integer userId, Integer[] roleList);
}
