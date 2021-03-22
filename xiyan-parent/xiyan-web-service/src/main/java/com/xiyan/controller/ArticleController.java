package com.xiyan.controller;

import com.xiyan.dto.ArticleDTO;
import com.xiyan.dto.ArticleReviewDTO;
import com.xiyan.dto.ArticleUpdateDTO;
import com.xiyan.dto.SortDTO;
import com.xiyan.service.ArticleService;
import com.xiyan.vo.ArticleVO;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author bright
 */
@Api(description = "文章集合")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private ArticleService articleService;


    @ApiOperation(value = "源码集合接口")
    @PostMapping("/list")
    public CommonListVO<ArticleVO> list(@RequestBody @Valid SortDTO sortDTO) {
        return articleService.list(sortDTO);
    }

    @ApiOperation(value = "根据id查询博客信息接口")
    @GetMapping("/byId/{id}")
    public BaseVO getArticleById(@PathVariable("id") Integer id) {
        return articleService.getArticleById(id);
    }


    @ApiOperation(value = "添加文章接口")
    @PostMapping("/add")
    public BaseVO add(@RequestBody @Valid ArticleDTO articleDTO) {
        return articleService.add(articleDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "修改文章接口")
    @PutMapping("/update/{id}")
    public BaseVO update(@RequestBody @Valid ArticleUpdateDTO articleUpdateDTO, @PathVariable("id") Integer id) {
        return articleService.update(articleUpdateDTO, id);
    }

    @ApiOperation(value = "浏览接口")
    @GetMapping("/browse/{id}")
    public BaseVO browse(@PathVariable("id") Integer id) {
        return articleService.browse(id);
    }

    @ApiOperation(value = "点赞接口")
    @GetMapping("/like/{id}")
    public BaseVO like(@PathVariable("id") Integer id) {
        return articleService.like(id);
    }

    @ApiOperation(value = "审核接口")
    @PutMapping("/review/{id}")
    public BaseVO review(@RequestBody ArticleReviewDTO articleReviewDTO, @PathVariable("id") Integer id) {
        return articleService.review(articleReviewDTO, id);
    }
}
