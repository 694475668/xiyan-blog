package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author bright
 */
@Data
public class ArticleUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章标题")
    @NotBlank(message = "文章标题不能为空")
    private String title;

    @ApiModelProperty(value = "文章封面")
    @NotBlank(message = "文章封面不能为空")
    private String pic;

    @ApiModelProperty(value = "文章标签")
    @NotBlank(message = "文章标签不能为空")
    private String tag;

    @ApiModelProperty(value = "简介")
    @NotBlank(message = "简介不能为空")
    private String remark;

    @ApiModelProperty(value = "文章内容")
    @NotBlank(message = "文章内容不能为空")
    private String content;

    @ApiModelProperty(value = "markdown 未转换html的内容")
    @NotBlank(message = "文章内容不能为空")
    private String markdownContent;

    @ApiModelProperty(value = "0. mavon-editor 1.editor-wang")
    @NotBlank(message = "编辑器内容不能为空")
    private String markdownType;

    @ApiModelProperty(value = "文章类型")
    @NotBlank(message = "文章类型不能为空")
    private String type;

}