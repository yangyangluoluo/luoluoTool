package com.luoluo.mt.excell;


import com.luoluo.mt.excell.batch.BatchProcess;
import com.luoluo.mt.excell.batch.BatchProcessResult;
import com.luoluo.mt.excell.rule.AbstractValidRule;
import com.luoluo.mt.excell.rule.ValidRule;
import com.luoluo.mt.excell.util.ExcelCellGetUtil;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: lijianguo
 * @time: 2021-03-24 9:29
 * @remark: excel 每一行的处理
 */
public class BatchProcessExcel implements BatchProcess<Row, List<List<ValidResult>>> {

    @ApiModelProperty(value = "验证的规则")
    private List<ValidRule> validRowRuleList;

    public BatchProcessExcel(List<ValidRule> validRowRuleList) {
        this.validRowRuleList = validRowRuleList;
    }

    @Override
    public BatchProcessResult<List<List<ValidResult>>> processData(List<Row> dataList, BatchProcessResult<List<List<ValidResult>>> processResult) {

        List<List<ValidResult>> resultList = new ArrayList<>(dataList.size());
        for (int i = 0; i < dataList.size(); i++) {
            List<ValidResult> rowResultList = new ArrayList<>(validRowRuleList.size());
            ValidResult beforeValidResult = null;
            for (int j = 0; j < validRowRuleList.size(); j++) {
                // 取每一个行验证规则 来验证此行的列数据(每一行定义了多个行的某列验证规则)
                ValidRule validRule = validRowRuleList.get(j);
                int cellIndex = validRule.validStart();
                List<Cell> ruleCellList = new ArrayList<>(validRule.validSum());
                for (int k = 0; k < validRule.validSum(); k++){
                    Cell cell = dataList.get(i).getCell(cellIndex);
                    ruleCellList.add(cell);
                    cellIndex++;
                }
                // 这里的 ruleCellList（为验证此含对应的定义的 哪几列 的数据） 通常是只有一个cell的，因为一般的定义验证都是一行中 的 某一列的校验，几列一起的 ruleCellList就会多个
                ValidResult pResult = beforeValidResult;
                beforeValidResult = processOneCell(ruleCellList, validRule, i + processResult.getDataBeginIndex(), pResult);
                if (pResult == null) {
                    rowResultList.add(beforeValidResult);
                }
                if (validRule instanceof AbstractValidRule){
                    AbstractValidRule absRule = (AbstractValidRule) validRule;
                    if (absRule.getRelatedNextValue() == false){
                        beforeValidResult = null;
                    }
                }
            }
            // 每一行验证完，就会添加一个List<ValidResult> 到验证结果中（这里可直接用属性赋值？）
            resultList.add(rowResultList);
        }
        processResult.setBatchResult(resultList);
        return processResult;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-24 09:37:00
     * @remark 验证
     */
    private ValidResult processOneCell(List<Cell> cellList, ValidRule validRule, Integer index, ValidResult beforeResult){

        if (beforeResult == null) {
            beforeResult = new ValidResult();
            beforeResult.setRow(index);
            beforeResult.setCol(validRule.validStart());
            beforeResult.setColSum(validRule.validSum());
            List<String> valueList = ExcelCellGetUtil.getCellListValue(cellList);
            beforeResult.setColDataList(valueList);
            beforeResult.setStatus(true);
        }
        validRule.validProcess(beforeResult);
        return beforeResult;
    }
}
