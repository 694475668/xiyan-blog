package com.xiyan.service;

import com.xiyan.dto.CommentAddDTO;
import com.xiyan.dto.CommentDTO;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommentVO;
import com.xiyan.vo.CommonListVO;

/**
 * @author: bright
 * @date:Created in 2020/12/13 12:37
 * @describe :
 */
public interface CommentService {
    /**
     * 根据id和类型查询评论列表
     *
     * @param commentDTO
     * @return
     */
    CommonListVO<CommentVO> list(CommentDTO commentDTO);

    /**
     * 添加评论
     *
     * @param commentAddDTO
     * @param id
     * @return
     */
    BaseVO add(CommentAddDTO commentAddDTO, Integer id);
}
