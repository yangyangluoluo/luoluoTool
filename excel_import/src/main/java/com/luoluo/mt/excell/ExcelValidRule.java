package com.luoluo.mt.excell;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: lijianguo
 * @time: 2020/12/16 14:22
 * @remark: 验证的注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelValidRule {

    /**  **/
    Class validClass() default Object.class;

    /** 失败的说明 **/
    String failDesc();

    /** true 可以空 false 不能为空 **/
    boolean empty() default true;

    /** 验证的总列数 **/
    int sum() default 1;

    /** 最小的长度 **/
    int minLength() default 1;

    /** 最大的长度 **/
    int maxLength() default 100000;

}
