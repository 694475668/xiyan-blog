package com.xiyan.controller;

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
import javax.validation.Valid;


/**
 * @author bright
 */
@Api(description = "文章集合")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;


    @ApiOperation(value = "审核接口")
    @PutMapping("/review/{id}")
    public BaseVO review(@RequestBody ArticleReviewDTO articleReviewDTO, @PathVariable("id") Integer id) {
        return articleService.review(articleReviewDTO, id);
    }

    @ApiOperation(value = "源码集合接口")
    @PostMapping("/list")
    public CommonListVO<ArticleVO> list(@RequestBody @Valid SortDTO sortDTO) {
        return articleService.list(sortDTO);
    }

    @ApiOperation(value = "源码集合接口")
    @DeleteMapping("/delete/{id}")
    public BaseVO delete(@PathVariable Integer id) {
        return articleService.delete(id);
    }

    @ApiOperation(value = "修改接口")
    @PutMapping("/update/{id}")
    public BaseVO update(@RequestBody @Valid ArticleUpdateDTO articleUpdateDTO, @PathVariable("id") Integer id) {
        return articleService.update(articleUpdateDTO, id);
    }
}
