package com.luoluo.mt.excell;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author: lijianguo
 * @time: 2020/12/14 11:24
 * @remark: 上传文件
 */
public interface UploadFile {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/14 11:29
    * @remark 请添加函数说明
    */
    String uploadGetUrl(MultipartFile multipartFile);

    /**
     * @author 李建国
     * @date 2022/1/30 18:31
     * describe 上传流的文件
     */
    String uploadStream(String var, InputStream is);
}
