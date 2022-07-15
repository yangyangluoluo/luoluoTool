package com.luoluo.mt.excell;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author: lijianguo
 * @time: 2021-05-27 23:09
 * @remark: 请添加类说明
 */
@Log4j2
public class UploadFileImpl implements UploadFile{

    public UploadFileImpl() {

    }

    @Override
    public String uploadGetUrl(MultipartFile multipartFile) {

        return "";
    }
    @Override
    public String uploadStream(String var, InputStream is) {


        return "url";
    }

}
