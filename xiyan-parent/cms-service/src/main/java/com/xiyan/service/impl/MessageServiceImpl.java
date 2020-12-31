package com.xiyan.service.impl;

import com.xiyan.domain.MessageDO;
import com.xiyan.dto.MessageDTO;
import com.xiyan.mapper.MessageMapper;
import com.xiyan.service.MessageService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.MessageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/3 9:48
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    @Override
    @Cacheable(cacheNames = "message", key = "#root.caches[0].name")
    public CommonListVO<MessageVO> list() {
        CommonListVO commonListVO = new CommonListVO();
        List<MessageDO> messageList = messageMapper.selectList(null);
        List<MessageDO> list = ObjectConvertUtil.convertInstance().objectConvert(messageList, MessageVO.class);
        commonListVO.setVoList(list);
        return commonListVO;
    }

    @Override
    @CacheEvict(cacheNames = "message", key = "#root.caches[0].name")
    public BaseVO add(MessageDTO messageDTO) {
        MessageDO message = new MessageDO();
        BeanUtils.copyProperties(messageDTO, message);
        message.setCreateTime(new Date());
        messageMapper.insert(message);
        return new BaseVO();
    }
}
