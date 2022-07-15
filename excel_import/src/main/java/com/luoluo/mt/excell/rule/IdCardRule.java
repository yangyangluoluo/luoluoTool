package com.luoluo.mt.excell.rule;

import cn.hutool.core.util.IdcardUtil;

/**
 * @author: lijianguo
 * @time: 2021-04-16 15:49
 * @remark: 身份证的验证
 */
public class IdCardRule extends AbstractValidRule {

    public IdCardRule(Integer colStart, Integer colSum, String failDesc) {
        super(colStart, colSum, failDesc);
    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return idCard(data);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-19 13:10:04
     * @remark 身份证的验证
     */
    private static boolean idCard(String idCardNo) {
        idCardNo = idCardNo.trim();
        int length = idCardNo.length();
        switch (length) {
            case 18:// 18位身份证
                return IdcardUtil.isValidCard18(idCardNo);
            case 15:// 15位身份证
                return IdcardUtil.isValidCard15(idCardNo);
            case 10: {// 10位身份证，港澳台地区
                String[] cardVal = IdcardUtil.isValidCard10(idCardNo);
                return null != cardVal && "true".equals(cardVal[2]);
            }
            default:
                return false;
        }
    }
}
