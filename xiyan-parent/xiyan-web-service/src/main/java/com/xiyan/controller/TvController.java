package com.xiyan.controller;

import com.xiyan.service.TvService;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.TvVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @version :
 * @author: bright
 * @date:Created in 2020/7/9 13:36
 */
@Api(tags = "电视集合")
@RestController
@RequestMapping("/tv")
public class TvController {

    @Resource
    private TvService tvService;

    @ApiOperation(value = "电视列表接口")
    @GetMapping("/list")
    public CommonListVO<TvVO> list() {
        return tvService.list();
    }
}
