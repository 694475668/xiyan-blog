package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.DictionaryDO;
import com.xiyan.dto.DictionaryByNameDTO;
import com.xiyan.dto.DictionaryDTO;
import com.xiyan.dto.DictionaryVagueDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.DictionaryMapper;
import com.xiyan.service.DictionaryService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Override
    public CommonListVO<DictionaryVO> list(DictionaryByNameDTO dictionaryByNameDTO) {
        CommonListVO<DictionaryVO> commonListVO = new CommonListVO<>();
        List<DictionaryDO> list = dictionaryMapper.selectList(new QueryWrapper<DictionaryDO>().eq("name", dictionaryByNameDTO.getName()));
        List<DictionaryVO> voList = ObjectConvertUtil.convertInstance().objectConvert(list, DictionaryVO.class);
        commonListVO.setVoList(voList);
        return commonListVO;
    }

    @Override
    public CommonListVO<DictionaryVagueVO> query(DictionaryVagueDTO dictionaryVagueDTO) {
        //解决初始参数解密后空数据是null问题,在做数据操作时候就会出现空指针异常
        if (StringUtils.isEmpty(dictionaryVagueDTO.getMeaning())) {
            dictionaryVagueDTO.setMeaning("");
        }
        if (StringUtils.isEmpty(dictionaryVagueDTO.getName())) {
            dictionaryVagueDTO.setName("");
        }
        CommonListVO<DictionaryVagueVO> commonListVO = new CommonListVO();
        List<DictionaryDO> dictionaryDOList = dictionaryMapper.selectList(null);
        dictionaryDOList = dictionaryDOList.stream().filter(s ->
                s.getName().indexOf(dictionaryVagueDTO.getName()) != -1 &&
                        s.getMeaning().indexOf(dictionaryVagueDTO.getMeaning()) != -1
        ).sorted(Comparator.comparing(DictionaryDO::getUpdateTime).reversed()).collect(Collectors.toList());
        //list的大小
        int count = dictionaryDOList.size();
        //总数据
        commonListVO.setTotal(Long.valueOf(count));
        //每页的开始数
        dictionaryVagueDTO.setPageNo((dictionaryVagueDTO.getPageNo() - 1) * dictionaryVagueDTO.getPageSize());
        //list进行分页
        dictionaryDOList = dictionaryDOList.subList(dictionaryVagueDTO.getPageNo(), count - dictionaryVagueDTO.getPageNo() > dictionaryVagueDTO.getPageSize() ? dictionaryVagueDTO.getPageNo() + dictionaryVagueDTO.getPageSize() : count);
        //在把集合赋值给前端要展示的对象
        List<DictionaryVagueVO> dictionaryVagueList = ObjectConvertUtil.convertInstance().objectConvert(dictionaryDOList, DictionaryVagueVO.class);

        commonListVO.setVoList(dictionaryVagueList);
        return commonListVO;
    }

    @Override
    public BaseVO add(DictionaryDTO dictionaryDTO, String username) {
        //同一个业务码下不允许存在相同编码
        DictionaryDO dictionary = dictionaryMapper.selectOne(new QueryWrapper<DictionaryDO>().and(dictionaryQueryWrapper -> dictionaryQueryWrapper.eq("name", dictionaryDTO.getName()).eq("value", dictionaryDTO.getValue())));
        if (null != dictionary) {
            return new BaseVO(false, ErrorCodeEnum.E0758.getKey(), ErrorCodeEnum.E0758.getValue());
        }
        DictionaryDO dictionaryDO = new DictionaryDO();
        BeanUtils.copyProperties(dictionaryDTO, dictionaryDO);
        dictionaryDO.setCreateTime(new Date());
        dictionaryDO.setCreateUser(username);
        dictionaryDO.setUpdateTime(new Date());
        dictionaryDO.setUpdateUser(username);
        dictionaryMapper.insert(dictionaryDO);
        return new BaseVO();
    }

    @Override
    public BaseVO update(DictionaryDTO dictionaryDTO, Integer id, String username) {
        DictionaryDO dictionaryDO = new DictionaryDO();
        BeanUtils.copyProperties(dictionaryDTO, dictionaryDO);
        dictionaryDO.setUpdateUser(username);
        dictionaryDO.setUpdateTime(new Date());
        dictionaryDO.setId(id);
        dictionaryMapper.updateById(dictionaryDO);
        return new BaseVO();
    }

    @Override
    public BaseVO delete(Integer id) {
        dictionaryMapper.deleteById(id);
        return new BaseVO();
    }

    @Override
    public DictionaryByIdVO queryDictionaryById(Integer id) {
        DictionaryDO dictionaryDO = dictionaryMapper.selectById(id);
        DictionaryByIdVO dictionaryByIdVO = new DictionaryByIdVO();
        BeanUtils.copyProperties(dictionaryDO, dictionaryByIdVO);
        return dictionaryByIdVO;
    }
}
