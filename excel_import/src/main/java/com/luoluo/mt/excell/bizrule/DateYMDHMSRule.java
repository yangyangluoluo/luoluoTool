package com.luoluo.mt.excell.bizrule;

import com.luoluo.mt.excell.ValidResult;
import com.luoluo.mt.excell.rule.AbstractValidRule;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author 李建国
 * @date 2022/2/4 14:58
 * describe 年月日 时分秒
 */
@Log4j2
@Component
public class DateYMDHMSRule extends AbstractValidRule {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void validProcess(ValidResult validResult) {
        List <String> dataList = validResult.getColDataList();
        String data = dataList.get(0);
        try {
            LocalDateTime l = LocalDateTime.parse(data, dtf);
            log.info(l);
        } catch (Exception e) {
            validResult.setStatus(false);
            validResult.setInvalidDesc("日期格式为yyyy-MM-dd HH:mm:ss");
        }
    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return false;
    }
}
