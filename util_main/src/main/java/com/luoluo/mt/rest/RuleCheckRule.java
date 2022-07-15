package com.luoluo.mt.rest;

import com.luoluo.mt.excell.ValidResult;
import com.luoluo.mt.excell.rule.AbstractValidRule;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @author lijianguo
 * @Date 2022/6/10 14:42
 * 请输入类的简介
 **/
@Log4j2
@Component
public class RuleCheckRule extends AbstractValidRule {

    @Override
    public void validProcess(ValidResult validResult) {

        log.info("===");
    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return false;
    }

}
