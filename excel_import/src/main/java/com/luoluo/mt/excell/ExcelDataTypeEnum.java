package com.luoluo.mt.excell;

/**
 * @author: lijianguo
 * @time: 2020/12/11 15:02
 * @remark: 请添加类说明
 */
public enum ExcelDataTypeEnum {

    ALL("所有的数据"),
    NONE("所有的数据"),
    SUC("该行全部成功的数据"),
    FAIL("该行有失败的数据");

    private String describe;

    ExcelDataTypeEnum(String describe) {
        this.describe = describe;
    }
}
