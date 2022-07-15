package com.luoluo.mt.excell.bizrule;

import com.luoluo.mt.excell.ValidResult;
import com.luoluo.mt.excell.rule.AbstractValidRule;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author 李建国
 * @date 2022/2/4 14:58
 * describe 年月日
 */
@Log4j2
@Component
public class DateYMDRule extends AbstractValidRule {


    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void validProcess(ValidResult validResult) {
        List <String> dataList = validResult.getColDataList();
        String data = dataList.get(0);
        try {
            LocalDate l = LocalDate.parse(data, dtf);
            log.info(l);
        } catch (Exception e) {
            validResult.setStatus(false);
            validResult.setInvalidDesc("日期格式为yyyy-MM-dd");
        }
    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return false;
    }
}
