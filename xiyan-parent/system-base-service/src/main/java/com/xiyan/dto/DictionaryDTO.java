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
public class DictionaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    @NotBlank(message = "编码不能为空")
    private String value;

    @ApiModelProperty(value = "业务码")
    @NotBlank(message = "业务码不能为空")
    private String name;

    @ApiModelProperty(value = "业务码名称")
    @NotBlank(message = "业务码名称不能为空")
    private String remark;

    @ApiModelProperty(value = "编码名称")
    @NotBlank(message = "编码名称不能为空")
    private String meaning;

}
