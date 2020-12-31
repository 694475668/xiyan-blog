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
 * @describe :
 */
@Data
@TableName("t_comment")
public class CommentDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer infoId;

    /**
     * 评论者
     */
    private Integer commentUserId;

    /**
     * 被评论者
     */
    private Integer targetUserId;

    /**
     * 内容
     */
    private String content;

    /**
     * 1.blog 2.code 3.shuoshuo
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;
}
