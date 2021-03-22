package com.xiyan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiyan.domain.CodeDO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: bright
 * @date:Created in 2020/11/22 18:25
 * @describe :
 */
public interface CodeMapper extends BaseMapper<CodeDO> {
    /**
     * 浏览+1
     *
     * @param id
     * @return
     */
    @Update("UPDATE t_code SET read_count=read_count+1 WHERE id=#{id}")
    int browse(Integer id);

    /**
     * 点赞+1
     *
     * @param id
     * @return
     */
    @Update("UPDATE t_code SET star_count=star_count+1 WHERE id=#{id}")
    int like(Integer id);

    /**
     * 标签 随机查询20条
     *
     * @return
     */
    @Select("SELECT distinct tag FROM t_code ORDER BY RAND() LIMIT 30;")
    List<CodeDO> queryTag();
}
