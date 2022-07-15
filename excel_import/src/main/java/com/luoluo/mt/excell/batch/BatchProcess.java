package com.luoluo.mt.excell.batch;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2021-03-23 17:03
 * @remark: 批处理接口
 */
public interface BatchProcess<T, R> {

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-23 17:12:24
     * @remark
     */
    BatchProcessResult<R> processData(List<T> dataList, BatchProcessResult<R> processResult);
}
