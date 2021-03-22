package com.xiyan.service;

import com.xiyan.dto.*;
import com.xiyan.vo.BaseVO;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/16 14:07
 */
public interface AttentionService {
    /**
     * 添加
     *
     * @param attentionAddDTO
     * @param id
     * @return
     */
    BaseVO add(AttentionAddDTO attentionAddDTO, Integer id);

    /**
     * 删除
     *
     * @param attentionDelDTO
     * @param id
     * @return
     */
    BaseVO delete(AttentionDelDTO attentionDelDTO, Integer id);

    /**
     * 查询文章是否被收藏
     *
     * @param attentionQueryDTO
     * @param id
     * @return
     */
    BaseVO query(AttentionQueryDTO attentionQueryDTO, Integer id);

}
