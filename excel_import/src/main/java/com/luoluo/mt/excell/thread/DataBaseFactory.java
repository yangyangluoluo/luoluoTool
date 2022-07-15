package com.luoluo.mt.excell.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: lijianguo
 * @time: 2021-03-26 8:36
 * @remark: 线程的工厂
 */
public class DataBaseFactory implements ThreadFactory {

    /** 线程的编号 **/
    private final AtomicInteger counter;

    /** 前缀 **/
    private String name;

    /** 注释 **/
    private List<String> stats;

    public DataBaseFactory(String name) {
        counter = new AtomicInteger(1);
        this.name = name;
        stats = new ArrayList<>();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread t = new Thread(runnable, name + "-Thread_" + counter.getAndIncrement());
        stats.add(String.format("Created thread %d with name %s on %s \n", t.getId(), t.getName(), new Date()));
        if (stats.size() > 1000){
            stats.clear();
        }
        return t;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-26 08:46:30
     * @remark 获取状态
     */
    public String getStats() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = stats.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        return sb.toString();
    }
}
