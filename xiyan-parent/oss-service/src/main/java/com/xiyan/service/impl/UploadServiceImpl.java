package com.xiyan.service.impl;

import com.xiyan.constants.Constant;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.service.UploadService;
import com.xiyan.util.QiNiuUtil;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/3 17:05
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    @Override
    public BaseVO upload(MultipartFile file, String fileType) throws Exception {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            log.error("传入的文件名不能为空");
            return new BaseVO(false, ErrorCodeEnum.E0804.getKey(), ErrorCodeEnum.E0804.getValue());
        }
        if (!this.validateFileName(fileName)) {
            log.error("文件名应仅包含汉字、字母、数字、下划线和点号");
            return new BaseVO(false, ErrorCodeEnum.E0805.getKey(), ErrorCodeEnum.E0805.getValue());
        }
        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
        String url = "";
        if (fileType.equals(Constant.IMAGE)) {
            url = new QiNiuUtil().upload(fileInputStream, Constant.IMAGE);
        } else if (fileType.equals(Constant.FILE)) {
            url = new QiNiuUtil().upload(fileInputStream, Constant.FILE);
        }
        FileVO fileVO = new FileVO();
        fileVO.setDownloadUrl(url);
        return fileVO;
    }

    @Override
    public BaseVO delete(String key, String fileType) throws Exception {
        new QiNiuUtil().delete(key, fileType);
        return new BaseVO();
    }

    /**
     * 验证文件名称：仅包含 汉字、字母、数字、下划线和点号
     *
     * @param fileName 文件名称
     * @return 返回true表示符合要求
     */
    private boolean validateFileName(String fileName) {
        String regex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5_\\.]+$";
        return fileName.matches(regex);
    }
}
