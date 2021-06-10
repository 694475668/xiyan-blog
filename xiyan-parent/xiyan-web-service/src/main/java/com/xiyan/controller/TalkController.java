package com.xiyan.controller;

import com.xiyan.dto.TalkAddDTO;
import com.xiyan.dto.TalkFindDTO;
import com.xiyan.dto.TalkUpdateDTO;
import com.xiyan.service.TalkService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.TalkListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author bright
 */
@Api(description = "说说集合")
@RestController
@RequestMapping("/talk")
public class TalkController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private TalkService talkService;


    @ApiOperation(value = "说说集合接口")
    @PostMapping("/list")
    public CommonListVO<TalkListVO> list(@RequestBody @Valid TalkFindDTO talkFindDTO) {
        return talkService.list(talkFindDTO);
    }

    @ApiOperation(value = "根据id查询信息接口")
    @GetMapping("/by/{id}")
    public BaseVO getTalkById(@PathVariable("id") Integer id) {
        return talkService.getTalkById(id);
    }


    @ApiOperation(value = "添加接口")
    @PostMapping("/add")
    public BaseVO add(@RequestBody @Valid TalkAddDTO talkAddDTO) {
        return talkService.add(talkAddDTO, Integer.valueOf(request.getHeader("User-ID")));
    }

    @ApiOperation(value = "修改接口")
    @PutMapping("/update/{id}")
    public BaseVO update(@RequestBody @Valid TalkUpdateDTO talkUpdateDTO, @PathVariable("id") Integer id) {
        return talkService.update(talkUpdateDTO, id);
    }

    @ApiOperation(value = "浏览接口")
    @GetMapping("/browse/{id}")
    public BaseVO browse(@PathVariable("id") Integer id) {
        return talkService.browse(id);
    }
}
