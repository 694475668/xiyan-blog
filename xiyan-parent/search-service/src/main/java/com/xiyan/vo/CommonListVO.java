package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 公共的数据返回对象
 *
 * @version V1.0:
 * @author: bright
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class CommonListVO<T> extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据总个数")
    private Long total;

    @ApiModelProperty(value = "数据集合")
    List<T> voList;
}
