package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author bright
 */
@Data
public class CommentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    private Integer id;

    @ApiModelProperty(value = "评论用户")
    private CommentUserVO commentUser;

    @ApiModelProperty(value = "被评论用户")
    private CommentUserVO targetUser;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论时间")
    private Date createDate;

}