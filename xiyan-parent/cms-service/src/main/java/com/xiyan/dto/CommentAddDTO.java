package com.xiyan.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: bright
 * @date:Created in 2020/12/13 11:48
 * @describe :
 */
@Data
public class CommentAddDTO implements Serializable {

    @ApiModelProperty(value = "文章id")
    @NotNull(message = "文章id不能为空")
    private Integer infoId;

    @ApiModelProperty(value = "被评论者")
    private Integer targetUserId;

    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty(value = "1.blog 2.code 3.shuoshuo")
    @NotBlank(message = "1.blog 2.code 3.shuoshuo不能为空")
    private String type;
}
