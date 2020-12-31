package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Description:
 *
 * @version 1.0:
 * @author: bright
 * @date:Created in 2020/7/22 16:12
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class AesVO extends BaseVO {

    @ApiModelProperty(value = "AES密钥")
    private String key;
}
