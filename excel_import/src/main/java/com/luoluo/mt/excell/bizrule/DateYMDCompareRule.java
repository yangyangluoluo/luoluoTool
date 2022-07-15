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
public class DateYMDCompareRule extends AbstractValidRule {


    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void validProcess(ValidResult validResult) {
        List <String> dataList = validResult.getColDataList();
        String data1 = dataList.get(0);
        String data2 = dataList.get(1);
        LocalDate l1 = null;
        LocalDate l2 = null;
        try {
            l1 = LocalDate.parse(data1, dtf);
            log.info(l1);
        } catch (Exception e) {
            validResult.setStatus(false);
            validResult.setInvalidDesc("日期格式为yyyy-MM-dd");
            return;
        }
        try {
            l2 = LocalDate.parse(data2, dtf);
            log.info(l2);
        } catch (Exception e) {
            validResult.setStatus(false);
            validResult.setInvalidDesc("日期格式为yyyy-MM-dd");
            return;
        }
        if (l1 != null && l2 != null){
            if (l2.isBefore(l1)){
                validResult.setStatus(false);
                validResult.setInvalidDesc(this.failDesc);
                return;
            }
        }

    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return false;
    }
}
