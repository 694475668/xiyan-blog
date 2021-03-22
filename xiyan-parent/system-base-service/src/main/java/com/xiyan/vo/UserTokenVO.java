package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version :
 * @author: 刘 明
 * @date:Created in 2020/6/24 17:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenVO extends BaseVO {

    @ApiModelProperty(value = "令牌")
    private String token;

    @ApiModelProperty(value = "用户对象")
    private UserVO userVO;
}
