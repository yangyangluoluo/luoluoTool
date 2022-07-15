package com.luoluo.mt.excell.batch;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: lijianguo
 * @time: 2021-03-24 8:49
 * @remark: 批处理的线程结构
 */
@Log4j2
@Data
public class BatchThread<R> {

    /** 总的条数 **/
    private Integer totalSize;

    /** 批的大小 **/
    private Integer batchSize;

    /** 局部传递的变量处理 **/
    private ThreadLocalDeliver deliver;

    /** 线程切换的模式 true的话 **/
    private Boolean newThreadMode = false;

    /** 线程池的静态变量 **/
    private static final ThreadPoolExecutor cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public BatchThread(Integer totalSize, Integer batchSize) {
        this.totalSize = totalSize;
        this.batchSize = batchSize;
        this.deliver = new InnerDeliver();
    }

    public BatchThread(Integer totalSize, Integer batchSize, ThreadLocalDeliver deliver) {
        this.totalSize = totalSize;
        this.batchSize = batchSize;
        this.deliver = deliver;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-19 11:45:01
     * @remark 创建划分的线程处理批次
     */
    public List<BatchProcessResult<R>> createBatchIndex(){
        List<BatchProcessResult<R>> batchSplitList = new ArrayList<>();
        if (batchSize == null || batchSize >= totalSize){
            BatchProcessResult result = new BatchProcessResult(0, 0, totalSize);
            batchSplitList.add(result);
        }else {
            int batchNum = totalSize / batchSize;
            int batchLeft = totalSize % batchSize;
            BatchProcessResult<R> lastResult = null;
            for (int i = 0; i < batchNum; i ++){
                lastResult = new BatchProcessResult(i, i * batchSize, (i + 1) * batchSize);
                batchSplitList.add(lastResult);
            }
            // 数量少于 1/2 不添加新的线程
            if (batchLeft > 0 && batchLeft < batchSize / 2){
                lastResult.setDataEndIndex(totalSize);
            }else {
                BatchProcessResult<R> result = new BatchProcessResult(lastResult.getIndex() + 1, (lastResult.getIndex()+1) * batchSize, totalSize);
                batchSplitList.add(result);
            }
        }
        return batchSplitList;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-19 09:54:54
     * @remark 在多个线程的验证
     */
    public List<BatchProcessResult<R>> validBatchGetResult(List rowList, BatchProcess batchProcessData){
        if (rowList.size() != totalSize){
            throw new RuntimeException("长度必须一致");
        }
        /** 线程池的并行 **/
        List<BatchProcessResult<R>> resultList = createBatchIndex();
        List<BatchProcessResult<R>> batchProcessResultList;
        if (resultList.size() == 1 && newThreadMode == false){
            batchProcessResultList = runInNowThread(rowList, batchProcessData, resultList);
        }else {
            batchProcessResultList = runInNewThread(rowList, batchProcessData, resultList);
        }
        return batchProcessResultList;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-07 08:45:35
     * @remark 在当前线程中运行
     */
    private List<BatchProcessResult<R>> runInNowThread(List rowList, BatchProcess batchProcessData, List<BatchProcessResult<R>> resultList){
        BatchProcessResult resultData = resultList.get(0);
        BatchProcessResult batchResult = batchProcessData.processData(rowList, resultData);
        List<BatchProcessResult<R>> batchProcessResultList = new ArrayList<>(resultList.size());
        batchProcessResultList.add(batchResult);
        return batchProcessResultList;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-07 08:43:53
     * @remark 在新线程中运行
     */
    private List<BatchProcessResult<R>> runInNewThread(List rowList, BatchProcess batchProcessData, List<BatchProcessResult<R>> resultList) {
        CompletionService<BatchProcessResult> completionService =  new ExecutorCompletionService(cachedThreadPool);
        List<Object> localList = deliver.getAllLocalData();
        for (BatchProcessResult resultData : resultList){

            List dataList = rowList.subList(resultData.getDataBeginIndex(), resultData.getDataEndIndex());
            completionService.submit(() -> {
                deliver.saveAllLocalData(localList);
                BatchProcessResult batchResult = batchProcessData.processData(dataList, resultData);
                return batchResult;
            });
        }
        List<BatchProcessResult<R>> batchProcessResultList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i ++){
            try {
                BatchProcessResult threadResult = completionService.take().get();
                batchProcessResultList.add(threadResult);
            } catch (InterruptedException e) {
                log.error(e);
                throw new RuntimeException(e.getMessage());
            } catch (ExecutionException e) {
                log.error(e);
                throw new RuntimeException(e.getMessage());
            }
        }
        Collections.sort(batchProcessResultList, (o1, o2) -> {
            if (o1.getIndex() > o2.getIndex()){
                return 1;
            }else {
                return -1;
            }
        });
        return batchProcessResultList;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-07 08:56:21
     * @remark 不获取返回结果，直接运行
     */
    public void validBatchNoResult(List rowList, BatchProcess batchProcessData){
        if (rowList.size() != totalSize){
            throw new RuntimeException("长度必须一致");
        }
        /** 线程池的并行 **/
        List<BatchProcessResult<R>> resultList = createBatchIndex();
        for (BatchProcessResult resultData : resultList){
            List dataList = rowList.subList(resultData.getDataBeginIndex(), resultData.getDataEndIndex());
            List<Object> localList = deliver.getAllLocalData();
            cachedThreadPool.execute(()-> {
                deliver.saveAllLocalData(localList);
                batchProcessData.processData(dataList, resultData);
            });
        }
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-26 09:17:36
     * @remark 默认的传递工具
     */
    private class InnerDeliver implements ThreadLocalDeliver{

        @Override
        public List<Object> getAllLocalData() {
            List<Object> localList = new ArrayList<>();
            return localList;
        }

        @Override
        public void saveAllLocalData(List<Object> objects) {

        }
    }

}
