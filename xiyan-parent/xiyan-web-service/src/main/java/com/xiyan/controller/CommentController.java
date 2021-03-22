package com.xiyan.controller;

import com.xiyan.dto.CommentAddDTO;
import com.xiyan.dto.CommentDTO;
import com.xiyan.service.CommentService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommentVO;
import com.xiyan.vo.CommonListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author: bright
 * @date:Created in 2020/12/13 13:40
 * @describe :
 */
@Api(description = "评论集合")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "根据id和类型查询评论列表接口")
    @PostMapping("/list")
    public CommonListVO<CommentVO> list(@RequestBody @Valid CommentDTO commentDTO) {
        return commentService.list(commentDTO);
    }

    @ApiOperation(value = "添加评论接口")
    @PostMapping("/add")
    public BaseVO add(@RequestBody @Valid CommentAddDTO commentAddDTO) {
        return commentService.add(commentAddDTO, Integer.valueOf(request.getHeader("User-ID")));
    }
}
