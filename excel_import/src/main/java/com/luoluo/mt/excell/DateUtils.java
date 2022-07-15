package com.luoluo.mt.excell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author: lijianguo
 * @time: 2021-05-27 20:39
 * @remark: 请添加类说明
 */
public class DateUtils {

    public static LocalDate StringToDate(String time)  {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");//代替simpleDateFormat
            return LocalDate.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
