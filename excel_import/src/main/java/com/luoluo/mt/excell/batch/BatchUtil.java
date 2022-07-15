package com.luoluo.mt.excell.batch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李建国
 * @date 2022/2/3 16:04
 * describe 添加分批
 */
public class BatchUtil {

    /**
     * @author 李建国
     * @date 2022/2/3 15:55
     * describe 分批次处理
     */
    public static  <T> List<List<T>> createBatchIndex(List<T> dataList, Integer batchSize){
        List<List<T>> batchSplitList = new ArrayList<>();
        if (batchSize == null || batchSize >= dataList.size()){
            batchSplitList.add(dataList);
        }else {
            int batchNum = dataList.size() / batchSize;
            int batchLeft = dataList.size() % batchSize;
            for (int i = 0; i < batchNum; i ++){
                batchSplitList.add(dataList.subList(i * batchSize,(i + 1) *batchSize));
            }
            if (batchLeft != 0){
                batchSplitList.add(dataList.subList(batchNum * batchSize, dataList.size()));
            }
        }
        return batchSplitList;
    }
}
