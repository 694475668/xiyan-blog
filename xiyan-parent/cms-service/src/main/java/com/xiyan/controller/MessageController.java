package com.xiyan.controller;

import com.xiyan.dto.MessageDTO;
import com.xiyan.service.MessageService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.MessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * @author bright
 */
@Api(description = "留言集合")
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;


    @ApiOperation(value = "留言列表接口")
    @GetMapping("/list")
    public CommonListVO<MessageVO> list() {
        return messageService.list();
    }

    @ApiOperation(value = "留言添加接口")
    @PostMapping("/add")
    public BaseVO add(@RequestBody @Valid MessageDTO messageDTO) {
        return messageService.add(messageDTO);
    }
}
