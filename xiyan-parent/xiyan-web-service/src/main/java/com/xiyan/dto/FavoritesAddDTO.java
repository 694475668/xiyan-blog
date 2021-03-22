package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/15 14:39
 */
@Data
public class FavoritesAddDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源id")
    @NotNull(message = "资源id不能为空")
    private Integer infoId;

    @ApiModelProperty(value = "类型")
    @NotBlank(message = "类型不能为空")
    private String type;
}
