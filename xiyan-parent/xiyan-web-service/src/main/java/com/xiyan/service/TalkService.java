package com.xiyan.service;

import com.xiyan.dto.TalkAddDTO;
import com.xiyan.dto.TalkFindDTO;
import com.xiyan.dto.TalkUpdateDTO;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.TalkListVO;

/**
 * @author: bright
 * @date:Created in 2020/11/22 18:28
 * @describe :
 */
public interface TalkService {
    /**
     * 说说列表
     *
     * @param talkFindDTO
     * @return
     */
    CommonListVO<TalkListVO> list(TalkFindDTO talkFindDTO);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    BaseVO getTalkById(Integer id);

    /**
     * 添加
     *
     * @param talkAddDTO
     * @param userId
     * @return
     */
    BaseVO add(TalkAddDTO talkAddDTO, Integer userId);

    /**
     * 浏览+1
     *
     * @param id
     * @return
     */
    BaseVO browse(Integer id);


    /**
     * 修改
     *
     * @param talkUpdateDTO
     * @param id
     * @return
     */
    BaseVO update(TalkUpdateDTO talkUpdateDTO, Integer id);
}
