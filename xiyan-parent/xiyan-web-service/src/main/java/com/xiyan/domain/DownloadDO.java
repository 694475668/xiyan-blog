package com.xiyan.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/21 18:01
 */
@Data
@TableName("t_download")
public class DownloadDO implements Serializable {
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
    private Integer codeId;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
