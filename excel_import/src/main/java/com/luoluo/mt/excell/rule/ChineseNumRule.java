package com.luoluo.mt.excell.rule;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

/**
 * @author: lijianguo
 * @time: 2021-04-19 13:43
 * @remark: 汉字和数字的验证
 */
public class ChineseNumRule extends AbstractValidRule {

    public static final String REG = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";

    public ChineseNumRule(Integer colStart, Integer colSum, String validDesc) {
        super(colStart, colSum, validDesc);
        if (StrUtil.isNotBlank(validDesc)){
            super.setFailDesc(super.failDesc + "格式错误");
        }
    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return Pattern.matches(REG, data);
    }
}
