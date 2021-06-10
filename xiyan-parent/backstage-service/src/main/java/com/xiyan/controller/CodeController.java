package com.xiyan.controller;

import com.xiyan.dto.CodeReviewDTO;
import com.xiyan.dto.CodeUpdateDTO;
import com.xiyan.dto.SortDTO;
import com.xiyan.service.CodeService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CodeVO;
import com.xiyan.vo.CommonListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * @author bright
 */
@Api(description = "源码集合")
@RestController
@RequestMapping("/code")
public class CodeController {

    @Resource
    private CodeService codeService;

    @ApiOperation(value = "审核接口")
    @PutMapping("/review/{id}")
    public BaseVO review(@RequestBody CodeReviewDTO codeReviewDTO, @PathVariable("id") Integer id) {
        return codeService.review(codeReviewDTO, id);
    }

    @ApiOperation(value = "源码集合接口")
    @PostMapping("/list")
    public CommonListVO<CodeVO> list(@RequestBody @Valid SortDTO sortDTO) {
        return codeService.list(sortDTO);
    }

    @ApiOperation(value = "源码集合接口")
    @DeleteMapping("/delete/{id}")
    public BaseVO delete(@PathVariable Integer id) {
        return codeService.delete(id);
    }

    @ApiOperation(value = "修改接口")
    @PutMapping("/update/{id}")
    public BaseVO update(@RequestBody @Valid CodeUpdateDTO codeUpdateDTO, @PathVariable("id") Integer id) {
        return codeService.update(codeUpdateDTO, id);
    }
}
