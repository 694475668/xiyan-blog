package com.xiyan.service;


import com.xiyan.dto.CodeReviewDTO;
import com.xiyan.dto.CodeUpdateDTO;
import com.xiyan.dto.SortDTO;
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
     * 审核
     *
     * @param codeReviewDTO
     * @param id
     * @return
     */
    BaseVO review(CodeReviewDTO codeReviewDTO, Integer id);

    /**
     * 博客列表
     *
     * @param sortDTO
     * @return
     */
    CommonListVO<CodeVO> list(SortDTO sortDTO);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    BaseVO delete(Integer id);

    /**
     * 修改
     *
     * @param codeUpdateDTO
     * @param id
     * @return
     */
    BaseVO update(CodeUpdateDTO codeUpdateDTO, Integer id);
}
