package com.luoluo.mt.excell.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: lijianguo
 * @time: 2021-03-26 8:34
 * @remark: 线程池的工具类
 */
public class ThreadPoolUtil {

    /** 线程池 **/
    private static final ThreadPoolExecutor cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool(new DataBaseFactory("database"));

    private static final ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(32, new DataBaseFactory("database"));

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-26 08:54:01
     * @remark 获取线程池
     */
    public static ThreadPoolExecutor getPool(){
        return cachedThreadPool;
    }

    public static ThreadPoolExecutor getFixedPool(){
        return fixedThreadPool;
    }

}
