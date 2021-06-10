package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.ResourceDO;
import com.xiyan.domain.RoleDO;
import com.xiyan.domain.UserDO;
import com.xiyan.dto.RoleAddDTO;
import com.xiyan.dto.RoleUpdateDTO;
import com.xiyan.dto.RoleVagueDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.ResourceMapper;
import com.xiyan.mapper.RoleMapper;
import com.xiyan.mapper.UserMapper;
import com.xiyan.service.RoleService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: 【 bright 】
 * @date: 【 2021/3/26 0026 10:28 】
 * @Description :
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public CommonListVO<RoleVO> list(Integer userId) {
        CommonListVO<RoleVO> commonListVO = new CommonListVO<>();
        //查询用户所属的角色，用户和角色是多对多的关系，因为一个用户可能有多少角色
        List<RoleDO> roleList = roleMapper.queryRoleByUserId(userId);
        List<RoleDO> roleDOList = new ArrayList<>();
        for (RoleDO roleDO : roleList) {
            roleDOList = getRoleList(roleDO.getId(), roleDOList);
        }
        //初始化HashSet对象，并把list对象元素赋值给HashSet对象
        HashSet set = new HashSet(roleDOList);
        //把List集合所有元素清空
        roleDOList.clear();
        //把HashSet对象添加至List集合
        roleDOList.addAll(set);
        List<RoleVO> list = ObjectConvertUtil.convertInstance().objectConvert(roleDOList, RoleVO.class);
        commonListVO.setVoList(list);
        return commonListVO;
    }

    @Override
    public CommonListVO<RoleVO> queryRolesByUserId(Integer id) {
        CommonListVO<RoleVO> commonListVO = new CommonListVO<>();
        List<RoleDO> roleList = roleMapper.queryRoleByUserId(id);
        List<RoleVO> list = ObjectConvertUtil.convertInstance().objectConvert(roleList, RoleVO.class);
        commonListVO.setVoList(list);
        return commonListVO;
    }

    @Override
    public CommonListVO<RoleListVO> list(RoleVagueDTO roleVagueDTO, Integer userId) {
        CommonListVO<RoleListVO> commonListVO = new CommonListVO<RoleListVO>();
        //查询当前登录用户所属角色
        List<RoleDO> roleList = roleMapper.queryRoleByUserId(userId);
        List<RoleDO> roleDOList = new ArrayList<>();
        for (RoleDO roleDO : roleList) {
            roleDOList = getRoleList(roleDO.getId(), roleDOList);
        }
        //资源权限去重
        HashSet set = new HashSet(roleDOList);
        //把List集合所有元素清空
        roleDOList.clear();
        //把HashSet对象添加至List集合
        roleDOList.addAll(set);
        //创建集合RoleVO
        List<RoleListVO> voArrayList = new ArrayList<>();
        //根据参数模糊查询，用的是jdk1.8的lambda表达式
        roleDOList = roleDOList.stream().filter(role -> role.getName().indexOf(roleVagueDTO.getName()) != -1).sorted(Comparator.comparing(RoleDO::getUpdateTime).reversed()).collect(Collectors.toList());
        //list的大小
        int count = roleDOList.size();
        //总数据
        commonListVO.setTotal(Long.valueOf(count));
        //每页的开始数
        roleVagueDTO.setPageNo(roleVagueDTO.getPageNo() - 1);
        roleDOList = roleDOList.subList(roleVagueDTO.getPageNo(), count - roleVagueDTO.getPageNo() > roleVagueDTO.getPageSize() ? roleVagueDTO.getPageNo() + roleVagueDTO.getPageSize() : count);
        for (RoleDO roleDO : roleDOList) {
            RoleListVO roleListVO = new RoleListVO();
            BeanUtils.copyProperties(roleDO, roleListVO);
            //根据角色id查询资源权限
            List<ResourceDO> resourceList = resourceMapper.queryResourceByRoleId(roleDO.getId(), false);
            List<RoleResourceVO> list = ObjectConvertUtil.convertInstance().objectConvert(resourceList, RoleResourceVO.class);
            roleListVO.setResourceList(list);
            voArrayList.add(roleListVO);
        }
        //在把集合赋值给前端要展示的对象
        commonListVO.setVoList(voArrayList);
        return commonListVO;
    }

    @Override
    public BaseVO add(RoleAddDTO roleAddDTO, Integer userId) {
        //获取当前用户所属角色列表
        List<RoleDO> roleDOList = roleMapper.queryRoleByUserId(userId);
        UserDO user = userMapper.selectById(userId);
        RoleDO role = new RoleDO();
        BeanUtils.copyProperties(roleAddDTO, role);
        //父级id
        role.setPid(roleDOList.get(0).getId());
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        role.setCreateUser(user.getUsername());
        role.setUpdateUser(user.getUsername());
        roleMapper.insert(role);
        //添加角色和资源的关系
        roleMapper.addResourceRole(role.getId(), roleAddDTO.getIdList());
        return new BaseVO();
    }

    @Override
    public BaseVO delete(Integer id) {
        //查询当前角色是否还有用户
        Integer count = roleMapper.hasUser(id);
        if (count > 0) {
            return new BaseVO(false, ErrorCodeEnum.E0754.getKey(), ErrorCodeEnum.E0754.getValue());
        }
        //如果删除的角色id还要子节点就不能进行删除
        List<RoleDO> roleDOList = roleMapper.selectList(new QueryWrapper<RoleDO>().eq("pid", id));
        if (roleDOList.size() > 0) {
            return new BaseVO(false, ErrorCodeEnum.E0755.getKey(), ErrorCodeEnum.E0755.getValue());
        }
        //删除对应资源与角色的关系
        roleMapper.delResourceRoleByRoleId(id);
        //删除角色
        roleMapper.deleteById(id);
        return new BaseVO();
    }

    @Override
    public RoleByIdVO queryRoleById(Integer id) {
        RoleByIdVO roleByIdVO = new RoleByIdVO();
        RoleDO roleDO = roleMapper.selectById(id);
        BeanUtils.copyProperties(roleDO, roleByIdVO);
        //查询当前角色所属资源
        List<ResourceDO> resourceList = resourceMapper.queryResourceByRoleId(id, true);
        Iterator<ResourceDO> iterator = resourceList.iterator();
        List<RoleResourceVO> voArrayList = new ArrayList<>();
        while (iterator.hasNext()) {
            ResourceDO resource = iterator.next();
            for (ResourceDO resourceDO : resourceList) {
                if (resourceDO.getPid() != null) {
                    if (resourceDO.getPid().equals(resource.getId())) {
                        iterator.remove();
                        resource.setId(null);
                        break;
                    }
                }
            }
            if (null != resource.getId()) {
                RoleResourceVO roleResourceVO = new RoleResourceVO();
                roleResourceVO.setId(resource.getId());
                roleResourceVO.setName(resource.getName());
                voArrayList.add(roleResourceVO);
            }
        }
        roleByIdVO.setResourceList(voArrayList);
        return roleByIdVO;
    }

    @Override
    public BaseVO update(RoleUpdateDTO roleUpdateDTO, Integer id, String username) {
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(roleUpdateDTO, roleDO);
        roleDO.setId(id);
        roleDO.setUpdateTime(new Date());
        roleDO.setUpdateUser(username);
        //1.先删除该角色下对应的所有资源对应关系
        roleMapper.delResourceRoleByRoleId(id);
        //2.执行角色修改
        int count = roleMapper.updateById(roleDO);
        if (count > 0) {
            //3.添加角色和资源的关系
            roleMapper.addResourceRole(id, roleUpdateDTO.getIdList());
        }
        return new BaseVO();
    }

    /**
     * 递归查询所属角色下的所有角色信息
     *
     * @param roleId
     * @param roles
     * @return
     */
    private List<RoleDO> getRoleList(Integer roleId, List<RoleDO> roles) {
        List<RoleDO> roleList = roleMapper.selectList(new QueryWrapper<RoleDO>().eq("pid", roleId));
        for (RoleDO roleDO : roleList) {
            roles.add(roleDO);
            roles = getRoleList(roleDO.getId(), roles);
        }
        return roles;
    }
}
