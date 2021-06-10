package com.xiyan.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.constants.Constant;
import com.xiyan.domain.BlackListDO;
import com.xiyan.dto.BlackListDTO;
import com.xiyan.dto.BlackListVagueDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.BlackListMapper;
import com.xiyan.service.BlackListService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.BlackListByIdVO;
import com.xiyan.vo.BlackListVagueVO;
import com.xiyan.vo.CommonListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 【 bright 】
 * @date: 【 2021/3/28 0028 12:53 】
 * @Description :
 */
@Service
public class BlackListServiceImpl implements BlackListService {

    @Resource
    private BlackListMapper blackListMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public CommonListVO<BlackListVagueVO> list(BlackListVagueDTO blackListVagueDTO) {
        //解决初始参数解密后空数据是null问题,在做数据操作时候就会出现空指针异常
        if (StringUtils.isEmpty(blackListVagueDTO.getValue())) {
            blackListVagueDTO.setValue("");
        }
        CommonListVO<BlackListVagueVO> commonListVO = new CommonListVO();
        List<BlackListDO> blackListDOList = blackListMapper.selectList(null);
        blackListDOList = blackListDOList.stream().filter(s ->
                s.getValue().indexOf(blackListVagueDTO.getValue()) != -1
        ).sorted(Comparator.comparing(BlackListDO::getUpdateTime).reversed()).collect(Collectors.toList());
        //list的大小
        int count = blackListDOList.size();
        //总数据
        commonListVO.setTotal(Long.valueOf(count));
        //每页的开始数
        blackListVagueDTO.setPageNo((blackListVagueDTO.getPageNo() - 1) * blackListVagueDTO.getPageSize());
        //list进行分页
        blackListDOList = blackListDOList.subList(blackListVagueDTO.getPageNo(), count - blackListVagueDTO.getPageNo() > blackListVagueDTO.getPageSize() ? blackListVagueDTO.getPageNo() + blackListVagueDTO.getPageSize() : count);
        //在把集合赋值给前端要展示的对象
        List<BlackListVagueVO> blackListVagueVOList = ObjectConvertUtil.convertInstance().objectConvert(blackListDOList, BlackListVagueVO.class);

        commonListVO.setVoList(blackListVagueVOList);
        return commonListVO;
    }

    @Override
    public BaseVO add(BlackListDTO blackListDTO, String username) {
        //同一个业务码下不允许存在相同编码
        BlackListDO blackList = blackListMapper.selectOne(new QueryWrapper<BlackListDO>().eq("value", blackListDTO.getValue()));
        if (null != blackList) {
            return new BaseVO(false, ErrorCodeEnum.E0758.getKey(), ErrorCodeEnum.E0758.getValue());
        }
        BlackListDO blackListDO = new BlackListDO();
        BeanUtils.copyProperties(blackListDTO, blackListDO);
        blackListDO.setCreateTime(new Date());
        blackListDO.setCreateUser(username);
        blackListDO.setUpdateTime(new Date());
        blackListDO.setUpdateUser(username);
        blackListMapper.insert(blackListDO);
        writeRedis();
        return new BaseVO();
    }

    @Override
    public BaseVO update(BlackListDTO blackListDTO, Integer id, String username) {
        BlackListDO blackListDO = new BlackListDO();
        BeanUtils.copyProperties(blackListDTO, blackListDO);
        blackListDO.setUpdateUser(username);
        blackListDO.setUpdateTime(new Date());
        blackListDO.setId(id);
        blackListMapper.updateById(blackListDO);
        writeRedis();
        return new BaseVO();
    }

    @Override
    public BaseVO delete(Integer id) {
        blackListMapper.deleteById(id);
        writeRedis();
        return new BaseVO();
    }

    @Override
    public BlackListByIdVO queryBlackListById(Integer id) {
        BlackListDO blackListDO = blackListMapper.selectById(id);
        BlackListByIdVO blackListByIdVO = new BlackListByIdVO();
        BeanUtils.copyProperties(blackListDO, blackListByIdVO);
        return blackListByIdVO;
    }

    /**
     * 写入数据到redis
     */
    private void writeRedis() {
        List<String> list = blackListMapper.list();
        redisTemplate.opsForValue().set(Constant.BLACK_LIST_KEY, JSONArray.toJSONString(list));
    }
}
