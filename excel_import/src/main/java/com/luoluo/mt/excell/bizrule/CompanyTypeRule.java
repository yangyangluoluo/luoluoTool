package com.luoluo.mt.excell.bizrule;

import com.luoluo.mt.excell.ValidResult;
import com.luoluo.mt.excell.rule.AbstractValidRule;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author 李建国
 * @date 2022/2/4 14:58
 * describe 公司类型
 */
@Log4j2
@Component
public class CompanyTypeRule extends AbstractValidRule {

    private List<String> names = Arrays.asList("分公司","子公司");

    @Override
    public void validProcess(ValidResult validResult) {
        List <String> dataList = validResult.getColDataList();
        String data = dataList.get(0);
        if (names.contains(data) == false){
            validResult.setStatus(false);
            validResult.setInvalidDesc("请输入分公司/子公司");
        }
    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return false;
    }
}
