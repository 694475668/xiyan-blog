package com.xiyan.service;


import com.xiyan.dto.*;
import com.xiyan.vo.*;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:38
 * @describe :
 */
public interface UserService {
    /**
     * 用户列表
     *
     * @param userVagueDTO
     * @param userId
     * @return
     */
    CommonListVO<UserDataVO> list(UserVagueDTO userVagueDTO, Integer userId);

    /**
     * 根据用户名获取密码，内部使用，不对外暴露
     *
     * @param username
     * @return
     */
    UserAuthVO getUserByUserName(String username);

    /**
     * 添加
     *
     * @param userAddDTO
     * @param userId
     * @param username
     * @return
     */
    BaseVO add(UserAddDTO userAddDTO, Integer userId, String username);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    UserByIdVO getUserById(Integer id);

    /**
     * 修改
     *
     * @param userUpdateDTO
     * @param id
     * @param username
     * @return
     */
    BaseVO update(UserUpdateDTO userUpdateDTO, Integer id, String username);

    /**
     * 解锁和锁定
     *
     * @param unlockAndLockDTO
     * @return
     */
    BaseVO unlockAndLock(UnlockAndLockDTO unlockAndLockDTO);


    /**
     * 删除
     *
     * @param id
     * @param userId
     * @return
     */
    BaseVO delete(Integer id, Integer userId);

    /**
     * 修改密码
     *
     * @param userPwdDTO
     * @param userId
     * @return
     */
    BaseVO updatePwd(UserPwdDTO userPwdDTO, Integer userId);

    /**
     * 授权
     *
     * @param userGrantDTO
     * @return
     */
    BaseVO authorization(UserGrantDTO userGrantDTO);
}
