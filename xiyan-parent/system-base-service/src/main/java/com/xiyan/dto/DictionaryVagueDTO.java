package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @version :
 * @author: bright
 * @date:Created in 2021-04-10 15:39
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DictionaryVagueDTO extends PageDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "数据含义")
    private String meaning;
}
