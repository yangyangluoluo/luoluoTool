package com.luoluo.mt.excell;

public enum ValidTypeEnum {
    // 身份证
    REGEX_ID_CARD( 1),
    // 电话号码
    REGEX_MOBILE(2),
    // 数字验证
    NUM(3),
    // 日期
    DATE(4),
    //中文数字
    CHINESE_NUM(5),
    // 非空的验证
    NOT_EMPTY(6),
    // Excel的日期
    DATE_EXCEL(8),
    // 用户自定义的验证方案
    USER_CUSTOMER(7);

    private Integer value;

    ValidTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
