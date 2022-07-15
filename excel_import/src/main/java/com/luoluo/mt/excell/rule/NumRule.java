package com.luoluo.mt.excell.rule;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * @author: lijianguo
 * @time: 2021-04-19 13:22
 * @remark:支持 各种 数字 情况的 的验证，通过指定数字类型 NumType
 */
@Getter
public class NumRule extends AbstractValidRule {

    /**是否需要限制数字大于= 0（非负数），默认不做此限制 **/
    private boolean notNegativeLimit =false;

    /**是否需要限制 数字类型的限制，默认不做此限制--则认为只要是数字 即可 **/
    private NumType NumTypeLimit = NumType.DIGIT;

    /** 数字 **/
    private static final String REGEX_NUM = "^-?\\d+(\\.\\d+)?$";
    /** 大于= 0 （非负数）的数字 **/
    private static final String NOT_NEGATIVE_NUM = "^\\d+(\\.\\d+)?$";

    public NumRule(Integer colStart, Integer colSum,NumType isPositiveLimit, String failDesc) {
        super(colStart, colSum, failDesc);
        if (failDesc == null){
            failDesc = "";
        }
        failDesc += "应为数字";
        super.setFailDesc(failDesc);
        this.NumTypeLimit = isPositiveLimit;
    }

    public NumRule(Integer colStart, Integer colSum, String failDesc) {
        super(colStart, colSum, failDesc);
        if (failDesc == null){
            failDesc = "";
        }
        failDesc += "应为数字";
        super.setFailDesc(failDesc);
    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        if (StrUtil.isEmpty(data)){
            return false;
        }
        boolean res = false;
        switch (getNumTypeLimit()) {
            case NOT_NEGATIVE_NUM:
                res = Pattern.matches(NOT_NEGATIVE_NUM, data);
                break;
            case DIGIT:
                res = Pattern.matches(REGEX_NUM, data);
        }
        return res;
    }
    @Getter
    @NoArgsConstructor
    @ApiModel(value = "NumType", description = "")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public  enum NumType{
        /**负数 **/
        NEGATIVE,

        /**非负数 **/
        NOT_NEGATIVE_NUM,

        /**
         * 大于0的数
         * **/
        Greater_Than_zero,

        /** 整数 **/
        INT,

        /***
         *普通数字，如 5.0 ，5，-6.0， 0.0， 990，-9等
         * */
        DIGIT
    }
}
