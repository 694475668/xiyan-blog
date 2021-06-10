package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.UserDO;
import com.xiyan.dto.*;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.UserMapper;
import com.xiyan.service.UserService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2021/1/22 13:38
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public CommonListVO<UserDataVO> list(UserVagueDTO userVagueDTO, Integer userId) {
        //解决初始参数解密后空数据是null问题,在做数据操作时候就会出现空指针异常
        if (StringUtils.isEmpty(userVagueDTO.getUsername())) {
            userVagueDTO.setUsername("");
        }
        if (StringUtils.isEmpty(userVagueDTO.getRoleId())) {
            userVagueDTO.setRoleId("");
        }
        CommonListVO<UserDataVO> commonListVO = new CommonListVO();
        List<UserDO> list = new ArrayList<>();
        list = getUserListByPid(userId, list);
        list = list.stream().filter(s ->
                s.getUsername().indexOf(userVagueDTO.getUsername()) != -1 &&
                        s.getRIds().indexOf(userVagueDTO.getRoleId()) != -1
        ).sorted(Comparator.comparing(UserDO::getUpdateTime).reversed()).collect(Collectors.toList());
        //list的大小
        int count = list.size();
        //总数据
        commonListVO.setTotal(Long.valueOf(count));
        //每页的开始数
        userVagueDTO.setPageNo((userVagueDTO.getPageNo() - 1) * userVagueDTO.getPageSize());
        //list进行分页
        list = list.subList(userVagueDTO.getPageNo(), count - userVagueDTO.getPageNo() > userVagueDTO.getPageSize() ? userVagueDTO.getPageNo() + userVagueDTO.getPageSize() : count);
        //在把集合赋值给前端要展示的对象
        List<UserDataVO> UserDataList = ObjectConvertUtil.convertInstance().objectConvert(list, UserDataVO.class);

        commonListVO.setVoList(UserDataList);
        return commonListVO;
    }

    @Override
    public UserAuthVO getUserByUserName(String username) {
        UserAuthVO userAuthVO = new UserAuthVO();
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", username));
        BeanUtils.copyProperties(userDO, userAuthVO);
        return userAuthVO;
    }

    @Override
    public BaseVO add(UserAddDTO userAddDTO, Integer userId, String username) {
        //判断用户名是否已经存在了
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", userAddDTO.getUsername()));
        if (null != user) {
            return new BaseVO(false, ErrorCodeEnum.E0751.getKey(), ErrorCodeEnum.E0751.getValue());
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userAddDTO, userDO);
        userDO.setCreateUser(username);
        userDO.setUpdateUser(username);
        userDO.setCreateTime(new Date());
        userDO.setUpdateTime(new Date());
        userDO.setPid(userId);
        userDO.setPassword(new BCryptPasswordEncoder().encode(userAddDTO.getPassword()));
        userMapper.insert(userDO);
        //添加用户对应的角色信息
        userMapper.addUserRole(userDO.getId(), userAddDTO.getIdList());
        return new BaseVO();
    }

    @Override
    public UserByIdVO getUserById(Integer id) {
        UserByIdVO userByIdVO = new UserByIdVO();
        UserDO userDO = userMapper.selectById(id);
        BeanUtils.copyProperties(userDO, userByIdVO);
        return userByIdVO;
    }

    @Override
    public BaseVO update(UserUpdateDTO userUpdateDTO, Integer id, String username) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userUpdateDTO, userDO);
        userDO.setId(id);
        userDO.setUpdateUser(username);
        userDO.setUpdateTime(new Date());
        userMapper.updateById(userDO);
        return new BaseVO();
    }

    @Override
    public BaseVO unlockAndLock(UnlockAndLockDTO unlockAndLockDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(unlockAndLockDTO, userDO);
        userMapper.updateById(userDO);
        return new BaseVO();
    }

    @Override
    public BaseVO delete(Integer id, Integer userId) {
        //1.不能操作自己
        if (id.equals(userId)) {
            return new BaseVO(false, ErrorCodeEnum.E0752.getKey(), ErrorCodeEnum.E0752.getValue());
        }
        //2.判断当前用户下是否还有子用户
        List<UserDO> userList = userMapper.queryUserByPid(id);
        if (userList.size() > 0) {
            return new BaseVO(false, ErrorCodeEnum.E0753.getKey(), ErrorCodeEnum.E0753.getValue());
        }
        //3.执行删除用户信息
        userMapper.deleteById(id);
        //4.删除用户下的角色关系
        userMapper.delUserRoleByUserId(id);
        return new BaseVO();
    }

    @Override
    public BaseVO updatePwd(UserPwdDTO userPwdDTO, Integer userId) {
        UserDO userDO = new UserDO();
        userDO.setId(userPwdDTO.getId());
        userDO.setPassword(new BCryptPasswordEncoder().encode(userPwdDTO.getPassword()));
        userMapper.updateById(userDO);
        return new BaseVO();
    }

    @Override
    public BaseVO authorization(UserGrantDTO userGrantDTO) {
        //删除用户原有的角色信息
        userMapper.delUserRoleByUserId(userGrantDTO.getUserId());
        //重新添加授权
        userMapper.addUserRole(userGrantDTO.getUserId(), userGrantDTO.getRoleIdList());
        return new BaseVO();
    }


    /**
     * 递归查询当期用户下所有子用户
     *
     * @param userId
     * @param userDOList
     * @return
     */
    private List<UserDO> getUserListByPid(Integer userId, List<UserDO> userDOList) {
        List<UserDO> userDOS = userMapper.queryUserByPid(userId);
        for (UserDO userDO : userDOS) {
            userDOList.add(userDO);
            userDOList = getUserListByPid(userDO.getId(), userDOList);
        }
        return userDOList;
    }
}
