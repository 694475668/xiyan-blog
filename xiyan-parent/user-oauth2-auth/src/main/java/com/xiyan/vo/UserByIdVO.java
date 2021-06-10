package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:33
 * @describe :
 */
@Data
public class UserByIdVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "金币")
    private Integer point;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "是否是会员")
    private String isMember;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
