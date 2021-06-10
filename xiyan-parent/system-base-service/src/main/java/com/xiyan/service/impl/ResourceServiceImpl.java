package com.xiyan.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.xiyan.constants.Constant;
import com.xiyan.domain.ResourceDO;
import com.xiyan.dto.ResourceAddDTO;
import com.xiyan.dto.ResourceByIdVO;
import com.xiyan.dto.ResourceUpdateDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.ResourceMapper;
import com.xiyan.mapper.RoleMapper;
import com.xiyan.service.ResourceService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.ResourceTreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: 【 bright 】
 * @date: 【 2021/3/27 0027 12:37 】
 * @Description :
 */
@Service
public class ResourceServiceImpl implements ResourceService {


    @Resource
    private ResourceMapper resourceMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public CommonListVO<ResourceTreeVO> queryResourceByUserId(Integer pid, Integer userId) {
        CommonListVO<ResourceTreeVO> commonListVO = new CommonListVO<>();
        //创建ResourceTreeVO集合对象
        List<ResourceTreeVO> resourceTree = new ArrayList<>();

        //根据pid查询资源信息
        List<ResourceDO> resourceDOList = resourceMapper.queryResource(pid, userId);

        //遍历集合对象
        for (ResourceDO resourceDO : resourceDOList) {
            //创建ResourceTreeVO对象
            ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
            resourceTreeVO.setId(resourceDO.getId());
            resourceTreeVO.setLabel(resourceDO.getName());
            //创建map集合用来存树形自定义参数
            Map<String, Object> treeMap = new HashMap<>(16);
            treeMap.put("url", resourceDO.getUrl());
            //获取上级资源
            ResourceDO queryParentDO = resourceMapper.selectById(resourceDO.getPid());
            //判断是否有上级节点,如果没有那这个节点就是根节点
            if (queryParentDO == null) {
                treeMap.put("superior", "");
            } else {
                treeMap.put("superior", queryParentDO.getName());
            }
            treeMap.put("remark", resourceDO.getRemark());

            //设置自定义属性
            resourceTreeVO.setAttributes(treeMap);

            //递归查询是否还有子节点
            pid = resourceDO.getId();
            CommonListVO<ResourceTreeVO> commonList = queryResourceByUserId(pid, userId);
            //有子节点就添加到子节点集合中
            resourceTreeVO.setChildren(commonList.getVoList());
            //把设置好的ResourceTreeVO对象添加到集合中
            resourceTree.add(resourceTreeVO);
        }
        commonListVO.setVoList(resourceTree);
        //存入redis方便网关进行判断接口鉴权
        if (resourceTree.size() > 0) {
            List<String> list = new ArrayList<>();
            list = getLastChildren(resourceTree, list);
            redisTemplate.opsForValue().set(Constant.AUTH_USER_KEY + userId, JSONArray.toJSONString(list));
        }
        return commonListVO;
    }

    @Override
    public CommonListVO<ResourceTreeVO> list(Integer pid) {
        CommonListVO<ResourceTreeVO> commonListVO = new CommonListVO<>();
        //创建ResourceTreeVO集合对象
        List<ResourceTreeVO> resourceTreeList = new ArrayList<>();
        //根据pid查询资源信息
        List<ResourceDO> resourceList = resourceMapper.queryResourceByPid(pid);
        for (ResourceDO resourceDO : resourceList) {
            //创建ResourceTreeVO对象
            ResourceTreeVO resourceTree = new ResourceTreeVO();
            resourceTree.setId(resourceDO.getId());
            resourceTree.setLabel(resourceDO.getName());
            //创建map集合用来存树形自定义参数
            Map<String, Object> treeMap = new HashMap<>();
            treeMap.put("url", resourceDO.getUrl());
            //设置资源类型名字
            treeMap.put("resourceType", resourceDO.getMeaning());
            //获取上级资源
            ResourceDO queryParent = resourceMapper.selectById(resourceDO.getPid());
            //判断是否有上级节点,如果没有那这个节点就是根节点
            if (queryParent == null) {
                treeMap.put("superior", "");
            } else {
                treeMap.put("superior", queryParent.getName());
            }
            treeMap.put("remark", resourceDO.getRemark());

            //设置自定义属性
            resourceTree.setAttributes(treeMap);
            //递归查询是否还有子节点
            pid = resourceDO.getId();
            CommonListVO<ResourceTreeVO> list = list(pid);
            //有子节点就添加到子节点集合中
            resourceTree.setChildren(list.getVoList());
            //把设置好的ResourceTreeVO对象添加到集合中
            resourceTreeList.add(resourceTree);
        }
        commonListVO.setVoList(resourceTreeList);
        return commonListVO;
    }

    @Override
    public BaseVO add(ResourceAddDTO resourceAddDTO, String username) {
        ResourceDO resourceDO = new ResourceDO();
        BeanUtils.copyProperties(resourceAddDTO, resourceDO);
        resourceDO.setCreateUser(username);
        resourceDO.setUpdateUser(username);
        resourceDO.setCreateTime(new Date());
        resourceDO.setUpdateTime(new Date());
        //添加资源
        resourceMapper.insert(resourceDO);
        //由于超管权限是拥有所有权限的，所以一添加资源就得添加超管和资源的权限关系
        roleMapper.addResourceRole(1, new Integer[]{resourceDO.getId()});
        return new BaseVO();
    }

    @Override
    public BaseVO delete(Integer id) {
        //1.查询当前要删除的ID是否还有子节点
        List<ResourceDO> resourceList = resourceMapper.queryResourceByPid(id);
        if (resourceList.size() > 0) {
            return new BaseVO(false, ErrorCodeEnum.E0756.getKey(), ErrorCodeEnum.E0756.getValue());
        }
        //2.判断该资源是否已经分配了角色
        int count = resourceMapper.hasRoleResource(id);
        if (count > 0) {
            return new BaseVO(false, ErrorCodeEnum.E0757.getKey(), ErrorCodeEnum.E0757.getValue());
        }
        //3.没有子节点就进行删除
        resourceMapper.deleteById(id);
        //删除角色与资源关系
        resourceMapper.delRoleResource(id);
        return new BaseVO();
    }

    @Override
    public BaseVO update(ResourceUpdateDTO resourceUpdateDTO, Integer id, String username) {
        ResourceDO resourceDO = new ResourceDO();
        BeanUtils.copyProperties(resourceUpdateDTO, resourceDO);
        resourceDO.setId(id);
        resourceDO.setUpdateTime(new Date());
        resourceDO.setUpdateUser(username);
        resourceMapper.updateById(resourceDO);
        return new BaseVO();
    }

    @Override
    public ResourceByIdVO queryResourceById(Integer id) {
        ResourceByIdVO resourceByIdVO = new ResourceByIdVO();
        ResourceDO resourceDO = resourceMapper.selectById(id);
        //获取父级pName
        ResourceDO resource = resourceMapper.selectById(resourceDO.getPid());
        BeanUtils.copyProperties(resourceDO, resourceByIdVO);
        if (null != resource) {
            resourceByIdVO.setPName(resource.getName());
        }
        return resourceByIdVO;
    }

    /**
     * 获取资源最后一个节点的url
     *
     * @param resourceTreeList
     * @param list
     * @return
     */
    List<String> getLastChildren(List<ResourceTreeVO> resourceTreeList, List<String> list) {
        for (ResourceTreeVO resourceTreeVO : resourceTreeList) {
            if (resourceTreeVO.getChildren().size() > 0) {
                getLastChildren(resourceTreeVO.getChildren(), list);
            } else {
                list.add(resourceTreeVO.getAttributes().get("url").toString());
            }
        }
        return list;
    }
}
