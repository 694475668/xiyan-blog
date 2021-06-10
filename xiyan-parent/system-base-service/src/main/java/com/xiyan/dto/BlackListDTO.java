package com.xiyan.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @version :
 * @author: bright
 * @date:Created in 2021-04-10 15:39
 */
@Data
public class BlackListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据值")
    @NotBlank(message = "数据值不能为空")
    private String value;

    @ApiModelProperty(value = "类型")
    @NotBlank(message = "类型不能为空")
    private String type;

}
