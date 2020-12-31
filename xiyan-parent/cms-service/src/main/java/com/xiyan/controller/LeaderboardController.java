package com.xiyan.controller;

import com.xiyan.service.LeaderboardService;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.LeaderboardVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author bright
 */
@Api(description = "排行榜集合")
@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Resource
    private LeaderboardService leaderboardService;


    @ApiOperation(value = "金币排行榜接口")
    @GetMapping("/gold")
    public CommonListVO<LeaderboardVO> gold() {
        return leaderboardService.gold();
    }

    @ApiOperation(value = "上传排行榜接口")
    @GetMapping("/upload")
    public CommonListVO<LeaderboardVO> upload() {
        return leaderboardService.upload();
    }

    @ApiOperation(value = "下载排行榜接口")
    @GetMapping("/download")
    public CommonListVO<LeaderboardVO> download() {
        return leaderboardService.download();
    }

    @ApiOperation(value = "收藏排行榜接口")
    @GetMapping("/favorites")
    public CommonListVO<LeaderboardVO> favorites() {
        return leaderboardService.favorites();
    }

}
