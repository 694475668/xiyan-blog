package com.xiyan.service;

import com.xiyan.dto.*;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CodeVO;
import com.xiyan.vo.CommonListVO;

/**
 * @author: bright
 * @date:Created in 2020/11/29 13:43
 * @describe :
 */
public interface CodeService {
    /**
     * 标签强
     *
     * @return
     */
    CommonListVO<CodeVO> tagList();


    /**
     * 源码列表
     *
     * @param sortDTO
     * @return
     */
    CommonListVO<CodeVO> list(SortDTO sortDTO);


    /**
     * 根据id查询源码
     *
     * @param id
     * @return
     */
    BaseVO getCodeById(Integer id);

    /**
     * 浏览+1
     *
     * @param id
     * @return
     */
    BaseVO browse(Integer id);

    /**
     * 点赞
     *
     * @param id
     * @return
     */
    BaseVO like(Integer id);

    /**
     * 添加
     *
     * @param codeAddDTO
     * @param userId
     * @return
     */
    BaseVO add(CodeAddDTO codeAddDTO, Integer userId);


    /**
     * 修改
     *
     * @param codeUpdateDTO
     * @param id
     * @return
     */
    BaseVO update(CodeUpdateDTO codeUpdateDTO, Integer id);


    /**
     * 下载
     *
     * @param codeDownloadDTO
     * @param id
     * @return
     */
    BaseVO download(CodeDownloadDTO codeDownloadDTO, Integer id);


    /**
     * 审核
     *
     * @param codeReviewDTO
     * @param id
     * @return
     */
    BaseVO review(CodeReviewDTO codeReviewDTO, Integer id);
}
