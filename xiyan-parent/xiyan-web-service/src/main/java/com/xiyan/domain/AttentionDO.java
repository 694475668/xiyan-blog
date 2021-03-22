package com.xiyan.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: bright
 * @date:Created in 2020/12/13 11:48
 * @describe : 关注
 */
@Data
@TableName("t_attention")
public class AttentionDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 被关注的用户id
     */
    private Integer attentionUserId;

    /**
     * 创建时间
     */
    private Date createTime;
}
