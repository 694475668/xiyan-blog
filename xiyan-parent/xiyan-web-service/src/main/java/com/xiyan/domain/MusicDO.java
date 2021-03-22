package com.xiyan.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: bright
 * @date:Created in 2020/11/15 9:11
 * @describe :
 */
@Data
@TableName("t_music")
public class MusicDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 歌曲名称
     */
    private String name;
    /**
     * 作者
     */
    private String artist;
    /**
     * 歌曲来源
     */
    private String url;
    /**
     * 歌曲封面
     */
    private String cover;

    /**
     * 歌词
     */
    private String lrc;
}
