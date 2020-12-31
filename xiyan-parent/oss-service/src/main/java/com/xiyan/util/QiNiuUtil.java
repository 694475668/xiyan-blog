package com.xiyan.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xiyan.constants.Constant;

import java.io.FileInputStream;
import java.util.UUID;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/3 17:39
 */
public class QiNiuUtil {

    /**
     * 将图片上传到七牛云
     */
    public String upload(FileInputStream file, String fileType) throws Exception {
        // zone0华东区域,zone1是华北区域,zone2是华南区域
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传
        Auth auth = Auth.create(Constant.accessKey, Constant.secretKey);
        String upToken = null;
        String path = null;
        String key = null;
        if (fileType.equals(Constant.IMAGE)) {
            upToken = auth.uploadToken(Constant.bucketPictureName);
            path = Constant.domainPicture;
        } else if (fileType.equals(Constant.FILE)) {
            upToken = auth.uploadToken(Constant.bucketFileName);
            path = Constant.domainFile;
            key = UUID.randomUUID() + ".zip";
        }
        Response response = uploadManager.put(file, key, upToken, null, null);
        // 解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return path + putRet.key;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public int delete(String fileName, String fileType) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        String key = fileName;
        String bucketName = "";
        if (fileType.equals(Constant.IMAGE)) {
            bucketName = Constant.bucketPictureName;
        } else if (fileType.equals(Constant.FILE)) {
            bucketName = Constant.bucketFileName;
        }
        Auth auth = Auth.create(Constant.accessKey, Constant.secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response delete = bucketManager.delete(bucketName, key);
            return delete.statusCode;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            ex.printStackTrace();
            return -1;
        }
    }
}
