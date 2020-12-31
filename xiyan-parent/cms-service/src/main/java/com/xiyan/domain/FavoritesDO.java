package com.xiyan.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/15 14:31
 */
@Data
@TableName("t_favorites")
public class FavoritesDO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Integer id;

    /**
     * 收藏者
     */
    private Integer userId;

    /**
     * 资源id
     */
    private Integer infoId;
    /**
     * 类型
     */
    private String type;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
