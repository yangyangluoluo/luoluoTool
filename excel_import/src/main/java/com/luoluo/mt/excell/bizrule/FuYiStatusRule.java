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
 * describe (1.未服役 2.退役申请中 3.退役已驳回 4.服役中 5.已退役)
 */
@Log4j2
@Component
public class FuYiStatusRule extends AbstractValidRule {

    public List<String> names = Arrays.asList("未服役","退役申请中",
            "退役已驳回","服役中","已退役");

    @Override
    public void validProcess(ValidResult validResult) {
        List <String> dataList = validResult.getColDataList();
        String data = dataList.get(0);
        if (names.contains(data) == false){
            validResult.setStatus(false);
            validResult.setInvalidDesc("服役状态错误");
        }
    }

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return false;
    }
}
