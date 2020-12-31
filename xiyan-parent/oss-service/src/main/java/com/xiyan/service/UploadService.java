package com.xiyan.service;

import com.xiyan.vo.BaseVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/3 17:02
 */
public interface UploadService {
    /**
     * 文件上传
     *
     * @param file
     * @param fileType
     * @return
     * @throws Exception
     */
    BaseVO upload(MultipartFile file, String fileType) throws Exception;

    /**
     * 删除文件
     *
     * @param key
     * @param fileType
     * @return
     * @throws Exception
     */
    BaseVO delete(String key, String fileType) throws Exception;
}
