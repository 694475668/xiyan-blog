package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author bright
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Integer id;

    @ApiModelProperty(value = "评论类型")
    @NotBlank(message = "评论类型不能为空")
    private String type;

}