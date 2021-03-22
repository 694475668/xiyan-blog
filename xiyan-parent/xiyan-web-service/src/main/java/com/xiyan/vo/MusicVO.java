package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: bright
 * @date:Created in 2020/11/15 9:11
 * @describe :
 */
@Data
public class MusicVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "歌曲名称")
    private String name;

    @ApiModelProperty(value = "作者")
    private String artist;

    @ApiModelProperty(value = "歌曲来源")
    private String url;

    @ApiModelProperty(value = "歌曲封面")
    private String cover;

    @ApiModelProperty(value = "歌词")
    private String lrc;
}
