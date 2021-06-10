package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:33
 * @describe :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryByNameDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据名称")
    private String name;

}
