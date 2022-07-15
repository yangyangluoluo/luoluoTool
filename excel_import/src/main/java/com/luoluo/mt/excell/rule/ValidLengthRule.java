package com.luoluo.mt.excell.rule;

import cn.hutool.core.util.ObjectUtil;
import com.luoluo.mt.excell.ValidResult;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2021-04-19 9:50
 * @remark: 验证的类型
 *
 * ValidLengthRule 用它的前提是必填项！！！即内部会有不为空的验证，
 * 如果是 需要对非必填项的单元格要求其如果填了数据才做长度的 校验 应该用 ValidNotEmptyRule 去做。
 *
 *    public  ValidLengthRule(Integer colStart, String lengthDesc) -->addOnlyNotNullRule
 */
@Getter
@Setter
public class ValidLengthRule extends AbstractValidRule {

    /** 最小的长度 必须为1**/
    private Integer minLength;

    /** 最大的长度 **/
    private Integer maxLength;


    /** 不为空 + 验证长度 -- 默认是对那一列的验证！ + 最小长度验证为1
     * 当 maxLength
     * @Author: lijiangguo
     * @Date: 2021-04-20 09:10:34
     * @remark 不关联下一个结果
     */
    public ValidLengthRule(Integer colStart, Integer colSum, String failDesc, Integer minLength, Integer maxLength) {
        super(colStart, colSum, failDesc);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public ValidLengthRule(Integer colStart, Integer colSum, String failDesc, Integer minLength, Integer maxLength, Boolean relatedNext) {
        super(colStart, colSum, failDesc, relatedNext);
        if (maxLength == null){
            throw new RuntimeException("必须设置字符串的最小长度");
        }
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
                setFailInfo(validResult, "长度不能为空");
                return;
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

    @Override
    protected boolean validOneNotNullData(String data, int index) {
        if (data.length() < minLength ){
            return false;
        }else if(maxLength != null && data.length() > maxLength){
            return false;
        }else {
            return true;
        }
    }
}
