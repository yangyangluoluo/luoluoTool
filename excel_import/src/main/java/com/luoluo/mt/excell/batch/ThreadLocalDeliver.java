package com.luoluo.mt.excell.batch;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2021-03-26 9:06
 * @remark: 线程的局部变量的传递
 */
public interface ThreadLocalDeliver {

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-26 09:06:41
     * @remark 先获取所有的要传递的局部变量
     */
    List<Object> getAllLocalData();

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-26 09:08:11
     * @remark 请添加方法注释
     */
    void saveAllLocalData(List<Object> objects);

}
