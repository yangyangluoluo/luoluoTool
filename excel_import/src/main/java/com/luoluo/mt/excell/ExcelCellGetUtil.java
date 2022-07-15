package com.luoluo.mt.excell;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ExcelCellGetUtil {

    /**
     * @auth lijianguo --获取cell 单元格们对应的字符串集合
     * @param cellList 单元格们，可以是一行row 的所有cells，也可以是指定某几个cell 的对应值集合
     * @remark getCellListValue
     */
    public static List<String> getCellListValue(List<Cell> cellList){

        List<String> dataList = new ArrayList<>(cellList.size());
        Integer i = 0;
        for (Cell cell: cellList){
            dataList.add(getCellValue(cell));
        }
        return dataList;
    }

    /**
     * @Author: --根据位置坐标获取在 某一行的数据中 特定的cell 的字符串值
     * @param rowList rowList
     * @param rowList rowList中行的下标
     * @param rowList rowList中列的下标
     * @remark 获取地 i，j,的string
     */
    public static String getCellString(List<Row> rowList, Integer i, Integer j){

        if (i > rowList.size()){
            return "";
        }
        Row row = rowList.get(i);
        if (row == null){
            return "";
        }
        Cell cell = row.getCell(j);
        if (cell == null){
            return "";
        }
        return getCellValue(cell);
    }

    /**
     * @auth --去获取cell的字符串值 cell为null 返回 null
     * -- 获取cell的String的值---并 去除掉 cell 的两端的空格信息
     * @date 2020/9/30 9:53
     * @remark 获取cell的String的值
     */
    public static String getCellValue(Cell cell){
        try {
            String cellStr;
            if (cell == null) {
                return null;
            } else {
                CellType cellType = cell.getCellType();
                if (cellType == CellType.NUMERIC) {
                    Double doubleValue = cell.getNumericCellValue();
                    if (doubleValue == null) {
                        cellStr = "";
                    } else {
                        DecimalFormat df = new DecimalFormat("#");
                        df.setMaximumFractionDigits(15);
                        cellStr = df.format(doubleValue);
                    }
                } else if (cellType == CellType.STRING) {
                    cellStr = cell.getStringCellValue();
                } else if (cellType == CellType.BLANK) {
                    cellStr = "";
                } else if (cellType == CellType.FORMULA) {
                    cellStr = cell.getCellFormula();
                } else {
                    throw new RuntimeException("该格式不支持");
                }
                if (cellStr != null) {
                    cellStr = cellStr.trim();
                }
                return cellStr;
            }
        }catch (Exception e){
            log.error(e);
            return "";
        }
    }

    /**
     * @author lijianguo
     * @Date  14:25  2021/11/12
     * 设置cell的值
     **/
    public static void setCellValue(Cell cell, Object value){
        if (ObjectUtil.isNull(value)){
            return;
        }
        if (value instanceof String){

            cell.setCellValue((String)value);
        }else if(value instanceof BigDecimal){

            cell.setCellValue(((BigDecimal) value).doubleValue());
        }else if(value instanceof Integer){

            cell.setCellValue(String.valueOf(value));
        }else {
            cell.setCellValue(String.valueOf(value));
        }
    }
}
