package com.luoluo.mt.excell;

import lombok.Data;

/**
 * @author: lijianguo
 * @time: 2021-05-27 23:17
 * @remark: 请添加类说明
 */
@Data
public class UploadPathNum {

    /** 注释 **/
    private Integer sucNUm = 0;

    /** 失败 **/
    private Integer failNum = 0;

    /** 路径 **/
    private String pathUrl;
}
