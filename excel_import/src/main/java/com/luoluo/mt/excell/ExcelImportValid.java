package com.luoluo.mt.excell;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.luoluo.mt.excell.batch.BatchProcess;
import com.luoluo.mt.excell.batch.BatchProcessResult;
import com.luoluo.mt.excell.batch.BatchThread;
import com.luoluo.mt.excell.rule.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author: lijianguo
 * @time: 2020/9/30 8:50
 * @remark: 导入数据的验证
 */
@Log4j2
public class ExcelImportValid<T>{

    @ApiModelProperty(value = "要验证的数据的行")
    private List<Row> rowList;

    @ApiModelProperty(value = "行的验证规则")
    private List<Object> validRowRuleList = new ArrayList<>();

    @ApiModelProperty(value = "列的验证规则")
    private List<ValidColRule> validColRuleList = new ArrayList<>();

    @ApiModelProperty(value = "验证的结果")
    private List<List<ValidResult>> validResult;

    @ApiModelProperty(value = "要验证的excel")
    private Workbook workbook;

    @ApiModelProperty(value = "一个线程处理的数据大小")
    private Integer recordBatchSize = null;

    @ApiModelProperty(value = "一个线程处理的数据大小")
    private Integer sheetNum = 0;

    @ApiModelProperty(value = "开始的row")
    private Integer startRow = 0;

    @ApiModelProperty(value = "写批注")
    private Boolean writeComment = true;

    public ExcelImportValid(Workbook workbook, int startRow) {
        ExcelCacheKeyUtil.clearAllKey();
        this.workbook = workbook;
        this.startRow = startRow;
        this.rowList = initRowList(workbook, startRow);
        log.info("===rowList===");
    }

    public ExcelImportValid(Integer sheetNum, Workbook workbook, int startRow) {
        ExcelCacheKeyUtil.clearAllKey();
        this.sheetNum = sheetNum;
        this.workbook = workbook;
        this.startRow = startRow;
        this.rowList = initRowList(workbook, startRow);
    }

    public void setWriteComment(Boolean writeComment) {
        this.writeComment = writeComment;
    }
    /**
     * @author lijianguo
     * @Date 2022/6/10 14:32
     * 获取数据
     **/
    public List<String> getWorkBookTitle(Integer idx){

        Sheet sheetAt = workbook.getSheetAt(sheetNum);
        List<Row> rowList = new ArrayList<>(sheetAt.getPhysicalNumberOfRows());
        Row title = null;
        Integer count = 0;
        for(Row row : sheetAt){
           title = row;
           if (title != null && idx == count){
               break;
           }
           count++;
        }
        if (title == null){
            return new ArrayList();
        }
        List<Cell> cells = new ArrayList<>();
        for (int i =0; i < title.getLastCellNum(); i++){
            Cell cell = title.getCell(i);
            cells.add(cell);
        }
        List<String> titleList = ExcelCellGetUtil.getCellListValue(cells);
        return titleList;
    }



    /**
     * @author lijianguo
     * @Date 2022/4/16-17:29
     * 获取非空的行
     **/
    public Integer getLastNotEmptyRowNum() {
        List<Boolean> allEmptyTag = new ArrayList<>();
        for (Row r : rowList){
            allEmptyTag.add(rowIsEmpty(r));
        }
        Integer count = allEmptyTag.size();
        Collections.reverse(allEmptyTag);
        for (Boolean tag : allEmptyTag){
            if (tag){
                count--;
            }else {
                break;
            }
        }
        return count;
    }

    /**
     * @author lijianguo
     * @Date 2022/4/16-17:28
     * 说明row是不是空的
     **/
    private boolean rowIsEmpty(Row r){

        List<Cell> cells = new ArrayList<>();
        for (int i =0; i < r.getLastCellNum(); i++){
            Cell cell = r.getCell(i);
            cells.add(cell);
        }
        List<String> dataList = ExcelCellGetUtil.getCellListValue(cells);
        for (String data : dataList){
            if (StrUtil.isNotBlank(data)){
                return false;
            }
        }
        return true;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-19 08:39:33
     * @remark 多线程的验证方案，一个线程处理的记录数量
     */
    public ExcelImportValid(Workbook workbook, int startRow, int recordBatchSize){
        this(workbook, startRow);
        this.recordBatchSize = recordBatchSize;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/12 16:34
     * @remark 初始化行的数据
     */
    private List<Row> initRowList(Workbook workbook, int startRow) {
        Sheet sheetAt = workbook.getSheetAt(sheetNum);
        List<Row> rowList = new ArrayList<>(sheetAt.getPhysicalNumberOfRows());
        for(Row row : sheetAt){
            if (row.getRowNum() >= startRow && row != null) {
                //第一行标题row.getRowNum是0 一般传来的是startRow是1，即标题行不会被放入rowList
                rowList.add(row);
            }
        }
        return rowList;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/27 13:24
     * @remark 类型空第几列验证 - 验证的个数 -- 功能和长度同事验证
     */
    public int addRule(ValidTypeEnum typeEnum, String failDesc, Integer colStart, Integer colSum, String lengthDesc, Integer min, Integer max){
        if (StringUtils.isBlank(lengthDesc)){
            throw new RuntimeException("必须有长度说明");
        }
        ValidLengthRule lengthRule = new ValidLengthRule(colStart, colSum, lengthDesc, min, max, true);
        validRowRuleList.add(lengthRule);
        addRule(typeEnum, failDesc, colStart, colSum);
        return validRowRuleList.size();
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-19 16:37:18
     * @remark 制作规则验证
     */
    public int addRule(ValidTypeEnum typeEnum, String failDesc, Integer colStart, Integer colSum){
        ValidRule validRule;
        if (ValidTypeEnum.REGEX_ID_CARD.equals(typeEnum)){
            validRule = new IdCardRule(colStart, colSum, failDesc);
        }else if (ValidTypeEnum.REGEX_MOBILE.equals(typeEnum)){
            validRule = new MobileRule(colStart, colSum, failDesc);
        }else if (ValidTypeEnum.NUM.equals(typeEnum)){
            validRule = new NumRule(colStart, colSum, failDesc);
        }else if (ValidTypeEnum.DATE.equals(typeEnum)){
            validRule = new DateRule(colStart, colSum, failDesc);
        }else if (ValidTypeEnum.CHINESE_NUM.equals(typeEnum)){
            validRule = new ChineseNumRule(colStart, colSum, failDesc);
        }else if (ValidTypeEnum.DATE_EXCEL.equals(typeEnum)){
            validRule = new ExcelDateRule(colStart, colSum, failDesc);
        }else {
            throw new RuntimeException("验证规则不存在");
        }
        validRowRuleList.add(validRule);
        return validRowRuleList.size();
    }

    /**
     * @Author: wu
     * @Date: 2021-04-19 15:09:59
     * @remark 不为空 + 验证长度 -- 默认是对那一列的验证！ + 最小长度验证为1
     * 当传入的max 为null 时，认为是  --> 目的是不做最大长度的限制，即只限制为空的验证
     */
    public int addLengthRule(Integer colStart, String colDesc,  Integer max){
        if (max==null){
            max = Integer.MAX_VALUE;
        }
        ValidLengthRule lengthRule = new ValidLengthRule(colStart, 1, colDesc, 1, max);
        validRowRuleList.add(lengthRule);
        return validRowRuleList.size();
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-19 15:09:59
     * @remark 只做验证长度
     */
    public int addRule(Integer colStart, Integer colSum, String lengthDesc, Integer min, Integer max){
        ValidLengthRule lengthRule = new ValidLengthRule(colStart, colSum, lengthDesc, min, max);
        validRowRuleList.add(lengthRule);
        return validRowRuleList.size();
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-05-28 11:37:51
     * @remark 添加验证的注解类
     */
    public boolean addRuleClass(Class cla){

        Object object = ReflectUtil.newInstanceIfPossible(cla);
        Field[] fields = ReflectUtil.getFieldsDirectly(object.getClass(), true);
        for (int i = 0; i < fields.length; i ++ ){
            Field field = fields[i];
            //final 和 static 字段跳过
            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            ExcelValidRule rule = field.getAnnotation(ExcelValidRule.class);
            if (rule == null){
                continue;
            }
            if (rule.validType() == ValidTypeEnum.USER_CUSTOMER){
                if (rule.empty() == false){
                    validRowRuleList.add(new ValidLengthRule(i, rule.sum(), rule.failDesc(), rule.minLength(), rule.maxLength(), true));
                }
                Map objectMap =  SpringUtil.getBeansOfType(rule.validClass());
                if (ObjectUtil.isNotNull(objectMap)&& objectMap.values().size() > 0){
                    AbstractValidRule validRule = (AbstractValidRule) objectMap.values().iterator().next();
                    validRule.setColStart(i);
                    validRule.setColSum(rule.sum());
                    validRule.setFailDesc(rule.failDesc());
                    addRule(validRule);
                }
            }else {
                if (rule.validType().equals(ValidTypeEnum.NOT_EMPTY)){
                    validRowRuleList.add(new ValidLengthRule(i, rule.sum(), rule.failDesc(), rule.minLength(), rule.maxLength()));
                }else {
                    if (rule.empty() == false){
                        validRowRuleList.add(new ValidLengthRule(i, rule.sum(), rule.failDesc(), rule.minLength(), rule.maxLength(), true));
                    }
                    addRule(rule.validType(),rule.failDesc(), i, rule.sum());
                }
            }
        }
        return true;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-19 15:09:59
     * @remark  基于长度验证的--》自己定义功能验证--至少会添加一个长度的验证！！
     */
    public int addRule(ValidRule validRule, String lengthDesc, Integer min, Integer max){
        ValidLengthRule lengthRule = new ValidLengthRule(validRule.validStart(), validRule.validSum(), lengthDesc, min, max, true);
        validRowRuleList.add(lengthRule);
        validRowRuleList.add(validRule);
        return validRowRuleList.size();
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-19 17:10:41
     * @remark 添加规则
     */
    public int addRule(ValidRule validRule){
        if (validRule.validStart() == null){
            throw new RuntimeException("起始列编号不能为空");
        }
        if (validRule.validSum() == null){
            throw new RuntimeException("总列数不能为空");
        }
        validRowRuleList.add(validRule);
        return validRowRuleList.size();
    }


    /**
    * @Author: lijiangguo
    * @Date: 2020/12/12 11:00
    * @remark 这些列必须满足唯一性
    */
    public void colUniqueRule(Integer col,String failDescribe){
        UniqueColRule colRule = new UniqueColRule(col, 0, rowList.size(), failDescribe);
        addColRule(colRule);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/12 10:55
    * @remark 添加列的验证规则
    */
    public void addColRule(ValidColRule colRule){

        if (colRule == null){
            throw new RuntimeException("列的验证规则不能为空");
        }
        if (colRule.validCol() == null){
            throw new RuntimeException("列不能为空");
        }
        if (colRule.validStartRow() == null){
            throw new RuntimeException("起始行不能为空");
        }
        if (colRule.validEndRow() == null){
            throw new RuntimeException("结束行不能为空");
        }
        validColRuleList.add(colRule);
    }

    /**
     * @auth lijianguo
     * @date 2020/9/30 12:23
     * @remark 验证成功 返回true 失败返回false
     */
    public boolean validAllSuc(){

        List<List<ValidResult>> resultList = validExcelResult();
        for (int i = 0; i < resultList.size(); i++){
            List<ValidResult> dataRow = resultList.get(i);
            for (int j = 0; j < dataRow.size(); j++){
                ValidResult result = dataRow.get(j);
                if (result.getStatus() == false){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @auth lijianguo
     * @date 2020/9/30 13:17
     * @remark 设置cell的错误的样式
     */
    private void setCellWrongStyle(){

        List<List<ValidResult>> resultList = validExcelResult();
        for (int i = 0; i < resultList.size(); i++){
            //resultList.get(i) 当前行所有单元格的验证集合结果
            List<ValidResult> validResultList = resultList.get(i);
            Row row = rowList.get(i);
            if (row == null){
                continue;
            }
            // 如果全部为空就不设置样式
            Boolean allEmpty = validDataEmpty(validResultList);
            if (allEmpty == true){
                continue;
            }
            for (ValidResult valid: validResultList){
                // 验证成功就不需要设置样式
                if (valid.getStatus() == null || valid.getStatus() == true){
                    continue;
                }
                List<Cell> validCellList = new ArrayList<>();
                for (int j = 0; j < valid.getColSum(); j++){

                    int index = valid.getCol() + j;
                    Cell cell = row.getCell(index);//获取原始数据
                    if (cell == null){
                        cell = row.createCell(index);
                    }
                    validCellList.add(cell);
                }
                setValidWrongCellStyle(validCellList, valid);
            }
        }
    }

    private Boolean validDataEmpty(List<ValidResult> validResultList) {
        Boolean allEmpty = true;
        for (ValidResult valid : validResultList){
            for (String data : valid.getColDataList()){
                if (data != null && data.length() > 0){
                    return false;
                }
            }
        }
        return allEmpty;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/15 16:31
     * @remark 色值错误的cell的样式--一行row的cellList 的错误信息对应一个 valid
     */
    private void setValidWrongCellStyle(List<Cell> cellList, ValidResult valid){

        Sheet sheet = workbook.getSheetAt(sheetNum);
        CellStyle style = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        style.setDataFormat(dataFormat.getFormat("@"));
        Font redFont = workbook.createFont();
        redFont.setColor(Font.COLOR_RED);
        style.setFont(redFont);

        if (valid.getIdDescList() != null && valid.getIdDescList().size() > 0){
            for (ValidResult.IdxDesc idxDesc :valid.getIdDescList()){
                Integer idx = Math.min(cellList.size()-1, idxDesc.getDescIdx());
                Cell cell = cellList.get(idx);
                extracted(idxDesc.getInvalidDesc(), sheet, cell);
            }
        }else {
            Cell cell = cellList.get(cellList.size()-1);
            cell.setCellStyle(style);
            extracted(valid.getInvalidDesc(), sheet, cell);
        }
    }

    /**
     * @author lijianguo
     * @Date 2022/6/11 12:19
     * 请输入方法的简介
     **/
    private void extracted(String validStr, Sheet sheet, Cell cell) {
        if (writeComment == true) {
            if (workbook instanceof HSSFWorkbook) {
                HSSFPatriarch p = (HSSFPatriarch) sheet.createDrawingPatriarch();
                if (cell.getCellComment() == null) {
                    HSSFComment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                    comment.setString(new HSSFRichTextString(validStr));
                    cell.setCellComment(comment);
                } else {
                    String oriCommentStr = cell.getCellComment().getString().getString();
                    String result = oriCommentStr + ";" + validStr;
                    cell.getCellComment().setString(new HSSFRichTextString(result));
                }
            } else {
                XSSFDrawing p = (XSSFDrawing) sheet.createDrawingPatriarch();
                if (cell.getCellComment() == null) {
                    try {
                        XSSFComment comment = p.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                        comment.setString(new XSSFRichTextString(validStr));
                        cell.setCellComment(comment);
                    } catch (Exception e) {
                        log.info(e);
                    }
                } else {
                    String oriCommentStr = cell.getCellComment().getString().getString();
                    String result = oriCommentStr + ";" + validStr;
                    cell.getCellComment().setString(new XSSFRichTextString(result));
                }
            }
        }else {
            cell.setCellValue(validStr);
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/9/30 9:42
     * @remark 验证Excel的表格 --- 这里会清除样式 + 清除每次验证相关的 缓存！！
     */
    private List<List<ValidResult>> validExcelResult(){
        if (validResult == null) {
            cleanExcelStyle();
            BatchThread<List<List<ValidResult>>> batchThread = new BatchThread<>(this.rowList.size(), this.recordBatchSize,new BatchExcelDeliver());
            List<BatchProcessResult<List<List<ValidResult>>>> splitList = batchThread.createBatchIndex();
            BatchProcessExcel batchProcessExcel = new BatchProcessExcel((List)this.validRowRuleList);
            if (splitList.size() <= 1){
                BatchProcessResult inputIndex = new BatchProcessResult(0, 0, this.rowList.size());
                BatchProcessResult<List<List<ValidResult>>> result = batchProcessExcel.processData(this.rowList, inputIndex);
                validResult = result.getBatchResult();
            }else {
                List<BatchProcessResult<List<List<ValidResult>>>> threadValidResultList = batchThread.validBatchGetResult(this.rowList, batchProcessExcel);
                for (BatchProcessResult<List<List<ValidResult>>> threadValidResult : threadValidResultList){
                    validResult.addAll(threadValidResult.getBatchResult());
                }
            }
            ValidColData(validResult);
        }
        ExcelCacheKeyUtil.clearAllKey();
        return validResult;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/14 17:30
     * @remark 对列的数据进行验证--列的验证规则
     */
    private void ValidColData(List<List<ValidResult>> resultList) {
        for (int i = 0; i < validColRuleList.size(); i++){
            ValidColRule colRule = validColRuleList.get(i);
            List<String> colDataList = new ArrayList<>(colRule.validEndRow() - colRule.validStartRow());
            for (int row = 0; row < rowList.size(); row++) {
                String cellStr = ExcelCellGetUtil.getCellString(this.rowList,row, colRule.validCol());
                colDataList.add(cellStr);
            }
            List<ValidResult> colResult = colRule.validColData(colDataList);
            if (colResult.size() != colDataList.size()){
                throw new RuntimeException("验证的行数不相等");
            }
            for (int row = 0; row < rowList.size(); row++){
                List<ValidResult> rowResultList = resultList.get(row);
                ValidResult theResult = colResult.get(row);
                rowResultList.add(theResult);
            }
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/10/27 12:53
     * @remark 清除表格的样式--所以只要验证都会清除样式
     */
    private void cleanExcelStyle(){

        CellStyle style = workbook.createCellStyle();
        Font redFont = workbook.createFont();
        redFont.setColor(Font.COLOR_NORMAL);
        style.setFont(redFont);
        for (int i = 0; i < rowList.size(); i++){
            Row row = rowList.get(i);
            int columnNum = row.getLastCellNum();
            for (int j = 0; j < columnNum; j++){
                Cell cell = row.getCell(j);
                if(cell == null){
                    cell = row.createCell(j);
                }
//                if (cell.getCellType() != null){
//                    cell.setCellStyle(style);
//                    cell.removeCellComment();
//                }
            }
        }
    }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 13:53
     * @remark 获取cell的data返回一个二维数组 type 1全部 2成功的data 3失败的data
     */
     public List<RowDataInfo> getCellDataToArray(ExcelDataTypeEnum type){

         List<List<ValidResult>> validList = validExcelResult();
         List<RowDataInfo> result = new ArrayList<>();
         Integer maxNum = getTotalColNum();
         for (int i = 0; i < validList.size(); i++){
             Boolean checkSuc = checkRowData(validList.get(i), type);
             if (checkSuc == true) {
                 RowDataInfo rowDataInfo = new RowDataInfo(i);
                 for (int j = 0; j < maxNum; j++) {
                     String cellValue = ExcelCellGetUtil.getCellString(this.rowList,i, j);
                     rowDataInfo.addColValue(cellValue);
                 }
                 result.add(rowDataInfo);
             }
         }
         return result;
     }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 16:36
     * @remark 转换为class
     */
    public List<T> getCellDataToArray(ExcelDataTypeEnum type, Class cla){

        List<RowDataInfo> result = getCellDataToArray(type);
        List obResult = new ArrayList<>();
        if (result.size() == 0){
            return obResult;
        }
        for (int i = 0; i < result.size(); i++) {
            Object object = ReflectUtil.newInstanceIfPossible(cla);
            Field[] fields = object.getClass().getDeclaredFields();
            RowDataInfo rowData = result.get(i);
            for (int j = 0; j < rowData.getColDataList().size(); j++) {
                String value = rowData.getColDataList().get(j);
                if (j < fields.length){
                    Field f = fields[j];
                    if (StringUtils.isNotBlank(value)) {
                        // 如果这里的字段为时间就需要转换
                        Object covertObject = ExcelDateUtil.covertToDate(value, f.getType());
                        try {
                            ReflectUtil.setFieldValue(object, f, covertObject);
                        }catch (Exception e){
                            log.error(e);
                        }
                    }
                }
            }
            obResult.add(object);
        }
        if (obResult.size() <= 0 || !(obResult.get(0) instanceof RowFailDescribe)){
            return obResult;
        }
        return setRowFailDescribe(obResult, result);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/16 11:44
    * @remark 设置错误的描述
    */
    private List<T> setRowFailDescribe(List<T> obResult, List<RowDataInfo> rowDataInfoList) {

        List<List<ValidResult>> validList = validExcelResult();
        for (int i = 0; i < obResult.size(); i++){
            RowFailDescribe o = (RowFailDescribe) obResult.get(i);
            RowDataInfo rowDataInfo = rowDataInfoList.get(i);
            List<ValidResult> rowList = validList.get(rowDataInfo.getRow());
            List<FailNumInfo> failInfoList = new ArrayList<>();
            for (int j = 0; j < rowList.size(); j++){
                ValidResult validResult = rowList.get(j);
                if (validResult.getStatus() == false){
                    FailNumInfo rowFailNumInfo = new FailNumInfo(j, validResult.getInvalidDesc());
                    failInfoList.add(rowFailNumInfo);
                }
            }
            o.setMsg(failInfoList);
        }
        return obResult;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 14:45
     * @remark 这一列的数据需要添加到返回的字符串 type 1全部 2成功的data 3失败的data
     */
     private boolean checkRowData(List<ValidResult> rowDataList, ExcelDataTypeEnum type){
         if (ExcelDataTypeEnum.ALL == type){
             return true;
         }
         if (ExcelDataTypeEnum.NONE == type){
             return false;
         }
         // 如果对着一行所有的验证都成功才为成功
         Boolean status = true;
         for (ValidResult col: rowDataList){
             if (col.getStatus() == false){
                 status = false;
                 break;
             }
         }
         if (ExcelDataTypeEnum.SUC == type){
             return status;
         }else {
             return !status;
         }
     }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 14:04
     * @remark 获取列的个数
     */
     private Integer getTotalColNum() {

         Integer maxNum = 0;
         for (int i = 0; i < rowList.size(); i++) {
             Row row = rowList.get(i);
             if (row != null) {
                 int columnNum = row.getLastCellNum();
                 maxNum = maxNum > columnNum ? maxNum : columnNum;
             }
         }
         return maxNum;
     }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 15:16
     * @remark 清空excel的 每一列的数据 type 1全部 2成功的data 3失败的data
     */
     public void clearRowAndSetStyle(ExcelDataTypeEnum type){
         List<List<ValidResult>> validList = validExcelResult();
         Sheet sheet = workbook.getSheetAt(sheetNum);
         List<List<ValidResult>> leftResultList = new ArrayList<>(validList.size());
         List<Row> leftRowList = new ArrayList<>(validList.size());
         int num = 0;
         for (int i = 0; i < validList.size(); i++){
             List<ValidResult> oneRow = validList.get(i);
             Boolean checkSuc = checkRowData(oneRow, type);
             // 删除这个类型的row
             Integer rowNum = i + 1 - num;
             Row row = sheet.getRow(rowNum + startRow - 1);
             if (checkSuc == true){
                 int rowNumData = row.getRowNum();
                 int sheetLastNumData = sheet.getLastRowNum();
                 sheet.removeRow(row);
                 if (rowNumData + 1 <= sheetLastNumData) {
                     sheet.shiftRows(rowNumData + 1, sheetLastNumData, -1);
                 }else {
                     sheet.shiftRows(rowNumData + 1, rowNumData + 1, -1);
                 }
                 num ++;
             }else {
                 leftResultList.add(oneRow);
                 leftRowList.add(row);
             }
         }
         for (int i = 0; i < leftResultList.size(); i++){
             List<ValidResult> oneRow = leftResultList.get(i);
             for (ValidResult oneResult: oneRow){
                 oneResult.setRow(i);
             }
         }
         this.validResult = leftResultList;
         this.rowList = leftRowList;
         setCellWrongStyle();
     }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/14 11:01
     * @remark 上传文件到
     */
     public String uploadFile(UploadFileInfo file, UploadFile gmisUploadFile){

         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         try {
             workbook.getSheetAt(0).setDefaultRowHeight((short) 600);
             workbook.write(bos);
         } catch (IOException e) {
             e.printStackTrace();
         }
         byte[] byteArray = bos.toByteArray();
         InputStream is = new ByteArrayInputStream(byteArray);
         String fileName = file.getFileName();
         if (StrUtil.isBlank(fileName)) {
             fileName = RandomUtil.randomNumbers(10) + "valid.xls";
         }
         String url = gmisUploadFile.uploadStream(fileName, is);
         return url;
     }

     /**
      * @Author: lijiangguo
      * @Date: 2021-03-23 16:54:30
      * @remark 多线程处理数据
      */
     public List<BatchProcessResult> processDataWithMulTread(ExcelDataTypeEnum type, Class cla, Integer batchSize, BatchProcess processDataByBatch){

         List<T> dataList = getCellDataToArray(type, cla);
         return processDataWithMulTread(dataList, batchSize, processDataByBatch);
     }

     /**
      * @Author: lijiangguo
      * @Date: 2021-03-24 11:23:55
      * @remark 多线程处理数据 batch > 1才开线程处理
      */
     public List<BatchProcessResult> processDataWithMulTread(List dataList, Integer batchSize, BatchProcess processDataByBatch){
         BatchThread batchThread = new BatchThread(dataList.size(), batchSize);
         if (batchThread.createBatchIndex().size() <= 1){
             BatchProcessResult inputIndex = new BatchProcessResult(0, 0, this.rowList.size());
             BatchProcessResult result = processDataByBatch.processData(dataList, inputIndex);
             return Arrays.asList(result);
         }else {
             List<BatchProcessResult> result = batchThread.validBatchGetResult(dataList, processDataByBatch);
             return result;
         }
     }

    /**
     * @author lijianguo
     * @Date  14:07  2021/11/12
     * 添加行的数据
     **/
    public void addRowToSheet(List<T> dataList,String desc){
        Sheet sheetAt = this.workbook.getSheetAt(sheetNum);

        CellStyle rStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        rStyle.setDataFormat(dataFormat.getFormat("@"));

        CellStyle wStyle = workbook.createCellStyle();
        wStyle.setDataFormat(dataFormat.getFormat("@"));
        Font redFont = workbook.createFont();
        redFont.setColor(Font.COLOR_RED);
        wStyle.setFont(redFont);

        Integer totalNum = sheetAt.getLastRowNum();
        for (T data : dataList){
            Row addRow = sheetAt.createRow(++ totalNum);
            Object[] objects = ReflectUtil.getFieldsValue(data);
            for (int i = 0; i < objects.length; i++){
                Cell rCell = addRow.createCell(i);
                rCell.setCellStyle(rStyle);
                ExcelCellGetUtil.setCellValue(rCell, objects[i]);
            }
            if (ObjectUtil.isNotNull(desc)){
                Cell wCell = addRow.createCell(objects.length);
                ExcelCellGetUtil.setCellValue(wCell, desc);
                wCell.setCellStyle(wStyle);
            }
        }
    }

    /**
     * @author lijianguo
     * @Date 2022/2/28-19:07
     * 获取非空的失败个数
     **/
    public Integer getNotEmptyNum(ExcelDataTypeEnum type){
        Integer total = 0;
        List<RowDataInfo> result = getCellDataToArray(type);
        for (RowDataInfo rowDataInfo: result) {
            if (getNotEmpty(rowDataInfo) == true) {
                total++;
            }
        }
        return total;
    }

    private Boolean getNotEmpty(RowDataInfo rowDataInfo) {
        List<String> dataOne = rowDataInfo.getColDataList();
        for (String str: dataOne){
            if (str != null && str.length() >0){
                return true;
            }
        }
        return false;
    }
}
