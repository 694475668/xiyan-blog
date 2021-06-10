package com.xiyan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiyan.domain.BlackListDO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: 【 bright 】
 * @date: 【 2021/4/12 0012 15:45 】
 * @Description :
 */

public interface BlackListMapper extends BaseMapper<BlackListDO> {
    /**
     * 列表
     *
     * @return
     */
    @Select("SELECT value FROM t_black_list")
    List<String> list();
}
