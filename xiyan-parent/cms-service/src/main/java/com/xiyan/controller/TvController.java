package com.xiyan.controller;

import com.xiyan.constants.Constant;
import com.xiyan.service.TvService;
import com.xiyan.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
