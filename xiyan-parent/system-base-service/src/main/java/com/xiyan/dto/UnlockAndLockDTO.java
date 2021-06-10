package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @version :
 * @author: 刘 明
 * @date:Created in 2021-03-17 13:39
 */
@Data
public class UnlockAndLockDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Integer id;

    @ApiModelProperty(value = "状态", required = true)
    @NotBlank(message = "状态不能为空")
    private String status;
}
