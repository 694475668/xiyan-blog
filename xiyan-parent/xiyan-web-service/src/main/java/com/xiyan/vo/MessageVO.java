package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: bright
 * @date:Created in 2020/11/15 9:11
 * @describe :
 */
@Data
public class MessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容")
    private String content;
}
