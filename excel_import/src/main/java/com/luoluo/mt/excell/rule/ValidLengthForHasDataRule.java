package com.luoluo.mt.excell.rule;

import cn.hutool.core.util.ObjectUtil;
import com.luoluo.mt.excell.ValidResult;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2021-04-27 15:59
 * @remark: 只验证非空的  ValidLengthForHasDataRule   只当单元格有数据时才做长度的 验证！！！
 * 对非必填项的单元格要求其如果填了数据才做长度的 校验 ，如果没有填写数据即单元格为空则true 认为是合法的。
 */
public class ValidLengthForHasDataRule extends AbstractValidRule {

    /** 最小的长度 必须为1**/
    private Integer minLength;

    /** 最大的长度 **/
    private Integer maxLength;

    public ValidLengthForHasDataRule(Integer colStart, Integer colSum, String failDesc, Integer minLength, Integer maxLength) {
        super(colStart, colSum, failDesc);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public void validProcess(ValidResult validResult) {
        // 如果验证失败就不再验证
        if (validResult.getStatus() == false){
            return;
        }
        List<String> dataList = validResult.getColDataList();
        for (int i = 0; i < dataList.size(); i++) {
            String data = dataList.get(i);
            if (ObjectUtil.isEmpty(data)) {
                continue;
            } else if (data.length() < minLength) {
                setFailInfo(validResult, "长度不够");
                return;
            } else if (maxLength != null && data.length() > maxLength) {
                setFailInfo(validResult, "长度超出" + maxLength + "限制");
                return;
            } else {

            }
        }
    }

    // 如果没有填写数据即单元格为空则true 认为是合法的
    /**
     此类由于重写了父类模板，而模板里又没调用到 钩子，那么，这个钩子 validOneNotNullData 其实
     *  不管写成撒样都没 任何意义的（没有被模板所调用到）！！！！！，但却又必须重写它！
     * */
    @Override
    protected boolean validOneNotNullData(String data, int index) {
        return true;
    }
}
