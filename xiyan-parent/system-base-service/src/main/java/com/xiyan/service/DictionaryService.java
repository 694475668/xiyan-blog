package com.xiyan.service;


import com.xiyan.dto.DictionaryByNameDTO;
import com.xiyan.dto.DictionaryDTO;
import com.xiyan.dto.DictionaryVagueDTO;
import com.xiyan.vo.*;

/**
 * @author: 【 bright 】
 * @date: 【 2021/3/28 0028 12:53 】
 * @Description :
 */
public interface DictionaryService {
    /**
     * 根据数据名称查询
     *
     * @param dictionaryByNameDTO
     * @return
     */
    CommonListVO<DictionaryVO> list(DictionaryByNameDTO dictionaryByNameDTO);

    /**
     * 数据列表
     *
     * @param dictionaryVagueDTO
     * @return
     */
    CommonListVO<DictionaryVagueVO> query(DictionaryVagueDTO dictionaryVagueDTO);

    /**
     * 添加
     *
     * @param dictionaryDTO
     * @param username
     * @return
     */
    BaseVO add(DictionaryDTO dictionaryDTO, String username);

    /**
     * 修改
     *
     * @param dictionaryDTO
     * @param id
     * @param username
     * @return
     */
    BaseVO update(DictionaryDTO dictionaryDTO, Integer id, String username);

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
    DictionaryByIdVO queryDictionaryById(Integer id);
}
