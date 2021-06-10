package com.xiyan.service;


import com.xiyan.dto.BlackListDTO;
import com.xiyan.dto.BlackListVagueDTO;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.BlackListByIdVO;
import com.xiyan.vo.BlackListVagueVO;
import com.xiyan.vo.CommonListVO;

/**
 * @author: 【 bright 】
 * @date: 【 2021/3/28 0028 12:53 】
 * @Description :
 */
public interface BlackListService {
    /**
     * 数据列表
     *
     * @param blackListVagueDTO
     * @return
     */
    CommonListVO<BlackListVagueVO> list(BlackListVagueDTO blackListVagueDTO);

    /**
     * 添加
     *
     * @param blackListDTO
     * @param username
     * @return
     */
    BaseVO add(BlackListDTO blackListDTO, String username);

    /**
     * 修改
     *
     * @param blackListDTO
     * @param id
     * @param username
     * @return
     */
    BaseVO update(BlackListDTO blackListDTO, Integer id, String username);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    BaseVO delete(Integer id);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    BlackListByIdVO queryBlackListById(Integer id);
}
