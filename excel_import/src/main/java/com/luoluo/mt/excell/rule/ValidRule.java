package com.luoluo.mt.excell.rule;

import com.luoluo.mt.excell.ValidResult;

/**
 * @author: lijianguo
 * @time: 2021-04-19 9:47
 * @remark: 验证的规则--尽量继承 AbstractValidRule
 */
public interface ValidRule {

    /**
     * @auth lijianguo
     * @date 2020/10/15 13:22
     * @remark 开始的列或者行的编号
     * @return
     */
    Integer validStart();

    /**
     * @auth lijianguo
     * @date 2020/10/12 16:40
     * @remark 验证的列数，行数
     * @return
     */
    Integer validSum();

    /**
     * @auth lijianguo
     * @date 2020/9/30 9:21
     * @remark 验证的失败的说明
     */
    String validFailDesc();

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/12 11:36
     * @remark 开始验证 整个数据 -这里的数据有可能为空
     */
    void validProcess(ValidResult validResult);
}
