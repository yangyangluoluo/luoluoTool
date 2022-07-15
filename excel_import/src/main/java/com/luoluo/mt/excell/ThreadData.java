package com.luoluo.mt.excell;

import lombok.Data;

/**
 * @author lijianguo
 * @Date 2022/6/10 14:50
 * 请输入类的简介
 **/
@Data
public class ThreadData {

    /**
     * @author lijianguo
     * @Date 2022/6/10 14:50
     * 现成的数据
     **/
    public static ThreadLocal<Object> theData = new ThreadLocal<>();

    /**
     * @author lijianguo
     * @Date 2022/6/12 16:19
     * 第二个缓存
     **/
    public static ThreadLocal<Object> theData2 = new ThreadLocal<>();

    /**
     * @author lijianguo
     * @Date 2022/6/10 14:52
     * 设置值
     **/
    public static void setValue(Object o){
        theData.set(o);
    }

    /**
     * @author lijianguo
     * @Date 2022/6/10 14:52
     * 获取置值
     **/
    public static Object getValue(){
        Object o = theData.get();
        return o;
    }

    /**
     * @author lijianguo
     * @Date 2022/6/10 14:52
     * 设置值
     **/
    public static void setValue2(Object o){
        theData2.set(o);
    }

    /**
     * @author lijianguo
     * @Date 2022/6/10 14:52
     * 获取置值
     **/
    public static Object getValue2(){
        Object o = theData2.get();
        return o;
    }

}
