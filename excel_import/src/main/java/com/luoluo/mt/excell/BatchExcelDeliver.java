package com.luoluo.mt.excell;


import com.luoluo.mt.excell.batch.ThreadLocalDeliver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2021-03-26 9:43
 * @remark: thread的传递
 */
public class BatchExcelDeliver implements ThreadLocalDeliver {

    @Override
    public List<Object> getAllLocalData() {
        List<Object> localList = new ArrayList<>();
        return localList;
    }

    @Override
    public void saveAllLocalData(List<Object> objects) {
    }

}
