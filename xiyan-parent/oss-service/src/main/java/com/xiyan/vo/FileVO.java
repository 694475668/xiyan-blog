package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/11/16 13:57
 */
@Data
public class FileVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件路径")
    private String downloadUrl;
}
