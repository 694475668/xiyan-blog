package com.xiyan.service;

import com.xiyan.dto.MessageDTO;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.MessageVO;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/3 9:46
 */
public interface MessageService {
    /**
     * 留言列表
     *
     * @return
     */
    CommonListVO<MessageVO> list();

    /**
     * 添加
     *
     * @param messageDTO
     * @return
     */
    BaseVO add(MessageDTO messageDTO);
}
