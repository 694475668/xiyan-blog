package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:33
 * @describe :
 */
@Data
public class BlackListByIdVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "数据值")
    private String value;

    @ApiModelProperty(value = "类型")
    private String type;
}
