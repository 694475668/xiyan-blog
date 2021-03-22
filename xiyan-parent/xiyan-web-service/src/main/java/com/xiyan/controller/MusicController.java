package com.xiyan.controller;

import com.xiyan.service.MusicService;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.MusicVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author bright
 */
@Api(description = "音乐集合")
@RestController
@RequestMapping("/music")
public class MusicController {

    @Resource
    private MusicService musicService;


    @ApiOperation(value = "音乐集合接口")
    @GetMapping("/list")
    public CommonListVO<MusicVO> list() {
        return musicService.list();
    }
}
