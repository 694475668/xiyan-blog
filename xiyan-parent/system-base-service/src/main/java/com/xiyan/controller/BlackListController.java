package com.xiyan.controller;


import com.xiyan.dto.BlackListDTO;
import com.xiyan.dto.BlackListVagueDTO;
import com.xiyan.service.BlackListService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.BlackListByIdVO;
import com.xiyan.vo.BlackListVagueVO;
import com.xiyan.vo.CommonListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author bright
 */
@Api(description = "黑名单集合")
@RestController
@RequestMapping("/black")
public class BlackListController {

    @Resource
    private BlackListService blackListService;

    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "集合数据列表")
    @PostMapping("/list")
    @ApiImplicitParam(name = "blackListVagueDTO", dataType = "BlackListVagueDTO")
    public CommonListVO<BlackListVagueVO> query(@RequestBody @Valid BlackListVagueDTO blackListVagueDTO) {
        return blackListService.list(blackListVagueDTO);
    }

    @ApiOperation(value = "添加接口")
    @PostMapping("/add")
    @ApiImplicitParam(name = "blackListDTO", dataType = "BlackListDTO")
    public BaseVO add(@RequestBody @Valid BlackListDTO blackListDTO) {
        return blackListService.add(blackListDTO, request.getHeader("User-NAME"));
    }

    @ApiOperation(value = "修改接口")
    @PutMapping("/update/{id}")
    @ApiImplicitParam(name = "id", value = "ID")
    public BaseVO update(@RequestBody @Valid BlackListDTO blackListDTO, @PathVariable Integer id) {
        return blackListService.update(blackListDTO, id, request.getHeader("User-NAME"));
    }

    @ApiOperation(value = "删除接口")
    @DeleteMapping("/delete/{id}")
    @ApiImplicitParam(name = "id", value = "id")
    public BaseVO delete(@PathVariable("id") Integer id) {
        return blackListService.delete(id);
    }

    @ApiOperation(value = "根据id查询接口")
    @GetMapping("/by/{id}")
    @ApiImplicitParam(name = "id", value = "ID")
    public BlackListByIdVO queryBlackListById(@PathVariable Integer id) {
        return blackListService.queryBlackListById(id);
    }
}
