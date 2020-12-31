package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * Description:
 * 加密后返回的数据
 *
 * @version 1.0:
 * @author: bright
 * @date:Created in 2020/7/22 17:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ResponseBodyVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "加密后的数据")
    private String responseData;
}
