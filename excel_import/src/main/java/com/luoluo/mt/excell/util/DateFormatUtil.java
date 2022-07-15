package com.luoluo.mt.excell.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author lijianguo
 * @Date 2022/6/11 11:51
 * 请输入类的简介
 **/
@Log4j2
public class DateFormatUtil {

    private static  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static  DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * @author lijianguo
     * @Date 2022/6/11 11:51
     * 请输入方法的简介
     **/
    public static boolean YMD(String dateStr){
        if (StrUtil.isBlank(dateStr)){
            return false;
        }
        try {
            LocalDate l1 = LocalDate.parse(dateStr, dtf);
            return true;
        } catch (Exception e1) {
            try {
                LocalDate l2 = LocalDate.parse(dateStr, dtf2);
                return true;
            }catch (Exception e2){
                return false;
            }
        }
    }

    /**
     * @author lijianguo
     * @Date 2022/6/11 11:57
     * 请输入方法的简介
     **/
    public static LocalDate getYMD(String dateStr){
        if (StrUtil.isBlank(dateStr)){
            return null;
        }
        try {
            LocalDate l1 = LocalDate.parse(dateStr, dtf);
            return l1;
        } catch (Exception e1) {
            try {
                LocalDate l2 = LocalDate.parse(dateStr, dtf2);
                return l2;
            }catch (Exception e2){
                return null;
            }

        }
    }



}
