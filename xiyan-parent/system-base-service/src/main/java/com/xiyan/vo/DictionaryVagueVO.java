package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version :
 * @author: bright
 * @date:Created in 2021-04-10 15:39
 */
@Data
public class DictionaryVagueVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据ID")
    private Integer id;

    @ApiModelProperty(value = "数据名称")
    private String name;

    @ApiModelProperty(value = "数据值")
    private String value;

    @ApiModelProperty(value = "数据含义")
    private String meaning;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
