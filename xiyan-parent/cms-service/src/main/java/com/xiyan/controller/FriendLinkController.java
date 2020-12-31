package com.xiyan.controller;

import com.xiyan.service.FriendLinkService;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.FriendLinkVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author bright
 */
@Api(description = "友链集合")
@RestController
@RequestMapping("/link")
public class FriendLinkController {

    @Resource
    private FriendLinkService friendLinkService;


    @ApiOperation(value = "友链列表接口")
    @GetMapping("/list")
    public CommonListVO<FriendLinkVO> list() {
        return friendLinkService.list();
    }
}
