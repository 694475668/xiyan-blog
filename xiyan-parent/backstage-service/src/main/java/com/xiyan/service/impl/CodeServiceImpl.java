package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.bo.CodeBO;
import com.xiyan.domain.CodeDO;
import com.xiyan.dto.CodeReviewDTO;
import com.xiyan.dto.CodeUpdateDTO;
import com.xiyan.dto.GetUserDTO;
import com.xiyan.dto.SortDTO;
import com.xiyan.feign.UserFeign;
import com.xiyan.mapper.CodeMapper;
import com.xiyan.repository.CodeRepository;
import com.xiyan.service.CodeService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CodeVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.UserByIdVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: bright
 * @date:Created in 2020/11/29 13:48
 * @describe :
 */
@Service
@Slf4j
public class CodeServiceImpl implements CodeService {

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private CodeRepository codeRepository;

    @Resource
    private UserFeign userFeign;

    @Override
    public BaseVO review(CodeReviewDTO codeReviewDTO, Integer id) {
        CodeDO codeDO = new CodeDO();
        codeDO.setId(id);
        codeDO.setState(codeReviewDTO.getState());
        codeMapper.updateById(codeDO);
        //插入es  由于有些字段是数据库默认赋值，所以我这里需要查询一遍在插入到ES中
        CodeDO article = codeMapper.selectById(codeDO.getId());
        CodeBO codeBO = new CodeBO();
        BeanUtils.copyProperties(article, codeBO);
        codeRepository.save(codeBO);
        return new BaseVO();
    }

    public CommonListVO<CodeVO> list(SortDTO sortDTO) {
        CommonListVO commonListVO = new CommonListVO();
        //解决初始参数解密后空数据是null问题,在做数据操作时候就会出现空指针异常
        if (StringUtils.isEmpty(sortDTO.getTitle())) {
            sortDTO.setTitle("");
        }
        if (StringUtils.isEmpty(sortDTO.getState())) {
            sortDTO.setState("");
        }
        QueryWrapper<CodeDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sortDTO.getTitle()), "title", sortDTO.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(sortDTO.getState()), "state", sortDTO.getState());
        queryWrapper.orderByDesc("create_time");
        //获取查询的数据
        List<CodeDO> codeDOList = codeMapper.selectList(queryWrapper);
        int count = codeDOList.size();
        //总条数
        commonListVO.setTotal(Long.valueOf(count));
        //每页的开始数
        sortDTO.setPageNo((sortDTO.getPageNo() - 1) * sortDTO.getPageSize());
        codeDOList = codeDOList.subList(sortDTO.getPageNo(), count - sortDTO.getPageNo() > sortDTO.getPageSize() ? sortDTO.getPageNo() + sortDTO.getPageSize() : count);
        List<CodeVO> codeVOList = new ArrayList<>();
        CodeVO codeVO = null;
        for (CodeDO codeDO : codeDOList) {
            codeVO = new CodeVO();
            UserByIdVO user = userFeign.getUser(new GetUserDTO(codeDO.getUserId(), null));
            log.info("用户信息【{}】", user);
            BeanUtils.copyProperties(codeDO, codeVO);
            codeVO.setName(user.getName());
            codeVOList.add(codeVO);
            commonListVO.setVoList(codeVOList);
        }
        return commonListVO;
    }

    @Override
    public BaseVO delete(Integer id) {
        codeMapper.deleteById(id);
        return new BaseVO();
    }

    @Override
    public BaseVO update(CodeUpdateDTO codeUpdateDTO, Integer id) {
        CodeDO codeDO = new CodeDO();
        BeanUtils.copyProperties(codeUpdateDTO, codeDO);
        codeDO.setUpdateTime(new Date());
        codeDO.setId(id);
        codeMapper.updateById(codeDO);
        //修改es数据
        CodeBO code = codeRepository.findArticleById(id);
        code.setBoutique(codeUpdateDTO.getBoutique());
        code.setTop(codeUpdateDTO.getTop());
        code.setRecommend(codeUpdateDTO.getRecommend());
        code.setOfficial(codeUpdateDTO.getOfficial());
        codeRepository.save(code);
        return new BaseVO();
    }
}
