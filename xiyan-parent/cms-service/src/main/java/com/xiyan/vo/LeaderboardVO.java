package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/2 15:28
 */
@Data
public class LeaderboardVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "会员标识")
    private String isMember;
}
