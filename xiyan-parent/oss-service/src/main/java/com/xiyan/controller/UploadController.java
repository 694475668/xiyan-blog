package com.xiyan.controller;

import com.xiyan.constants.Constant;
import com.xiyan.service.UploadService;
import com.xiyan.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/3 17:14
 */
@Api(tags = "文件集合")
@RestController
public class UploadController {
    @Resource
    private UploadService uploadService;

    @ApiOperation(value = "上传文件接口")
    @PostMapping(value = "/file/upload")
    public BaseVO fileUpload(MultipartFile file) throws Exception {
        return uploadService.upload(file, Constant.FILE);
    }

    @ApiOperation(value = "上传图片接口")
    @PostMapping(value = "/image/upload")
    public BaseVO imageUpload(MultipartFile file) throws Exception {
        return uploadService.upload(file, Constant.IMAGE);
    }

    @ApiOperation(value = "删除图片接口")
    @DeleteMapping(value = "/image/delete/{key}")
    public BaseVO imageDelete(@PathVariable("key") String key) throws Exception {
        return uploadService.delete(key, Constant.IMAGE);
    }

    @ApiOperation(value = "删除文件接口")
    @DeleteMapping(value = "/file/delete/{key}")
    public BaseVO fileDelete(@PathVariable("key") String key) throws Exception {
        return uploadService.delete(key, Constant.FILE);
    }
}
