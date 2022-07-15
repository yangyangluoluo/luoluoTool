package com.luoluo.mt.excell.rule;

import java.util.regex.Pattern;

/**
 * @author: lijianguo
 * @time: 2021-04-19 13:40
 * @remark: 日期的验证
 */
public class DateRule extends AbstractValidRule {

    /** 日期的正则 **/
    public static final String DATE ="[0-9]{4}-[0-9]{2}-[0-9]{2}";

    public DateRule(Integer colStart, Integer colSum, String failDesc) {
        super(colStart, colSum, failDesc);
    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return Pattern.matches(DATE, data);
    }
}
