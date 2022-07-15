package com.luoluo.mt.excell.rule;

import java.util.regex.Pattern;

/**
 * @author: lijianguo
 * @time: 2021-04-19 13:17
 * @remark: 请添加类说明
 */
public class MobileRule extends AbstractValidRule {

    /** 手机号码的验证 **/
    private static String REGEX_MOBILE = "^[1](([3][0-9])|([4][5,7,9])|([5][^4,6,9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$";

    public MobileRule(Integer colStart, Integer colSum, String failDesc) {
        super(colStart, colSum, failDesc);
    }


    @Override
    protected boolean validOneNotNullData(String data, int index) {

        return Pattern.matches(REGEX_MOBILE, data);
    }
}
