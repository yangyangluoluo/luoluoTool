package com.luoluo.mt.excell.rule;

import com.luoluo.mt.excell.ValidResult;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 李建国
 * @date 2022/2/4 14:58
 * describe 门店状态
 */
@Log4j2
public class ExcelDateRule extends AbstractValidRule {

    /** 日期的正则 **/
    public static final String DATE ="^[1-9]\\d{3}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1])";

    public ExcelDateRule(Integer colStart, Integer colSum, String failDesc) {
        super(colStart, colSum, failDesc);
    }

    @Override
    public void validProcess(ValidResult validResult) {
        List <String> dataList = validResult.getColDataList();
        String data = dataList.get(0);
        Boolean status = Pattern.matches(DATE, data);
        if (status == false){
            validResult.setStatus(false);
            validResult.setInvalidDesc("日期格式错误例: 2012/12/12");
        }
    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return false;
    }
}
