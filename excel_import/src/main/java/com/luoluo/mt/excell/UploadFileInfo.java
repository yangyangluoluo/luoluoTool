package com.luoluo.mt.excell;

import lombok.Data;

/**
 * @author: lijianguo
 * @time: 2020/12/14 14:16
 * @remark: 请添加类说明
 */
@Data
public class UploadFileInfo {

    /** 文件的名字 **/
    private String fileName = "";

    /** 原始名字 **/
    private String oriName = "oriName.xlsx";

    /** 类型 **/
    private String contentType = "contentType.xlsx";

    /** 类型 **/
    private String bizType = "bizType";

}
