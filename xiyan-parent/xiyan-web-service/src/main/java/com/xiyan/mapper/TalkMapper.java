package com.xiyan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiyan.domain.TalkDO;
import org.apache.ibatis.annotations.Update;

/**
 * @author: bright
 * @date:Created in 2020/11/22 18:25
 * @describe :
 */
public interface TalkMapper extends BaseMapper<TalkDO> {

    /**
     * 浏览+1
     *
     * @param id
     * @return
     */
    @Update("UPDATE t_talk SET read_count=read_count+1 WHERE id=#{id}")
    int browse(Integer id);
}
