package com.luoluo.mt.excell.batch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lijianguo
 * @time: 2021-03-19 10:08
 * @remark: 这个线程的验证的数据的结果
 */
@Data
public class BatchProcessResult<T> {

    @ApiModelProperty(value = "第几个batch")
    private int index;

    @ApiModelProperty(value = "数据的开始的index")
    private int dataBeginIndex;

    @ApiModelProperty(value = "数据的结束的index")
    private int dataEndIndex;

    @ApiModelProperty(value = "第几个batch")
    private T batchResult;

    public BatchProcessResult(int index, int dataBeginIndex, int dataEndIndex) {
        this.index = index;
        this.dataBeginIndex = dataBeginIndex;
        this.dataEndIndex = dataEndIndex;
    }

}
