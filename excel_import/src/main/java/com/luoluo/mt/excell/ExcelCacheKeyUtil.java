package com.luoluo.mt.excell;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: lijianguo
 * @time: 2021-03-08 13:35
 * @remark: 保存一个key
 */
@Component
public class ExcelCacheKeyUtil {

    /** 保存所有的key的集合 **/
    private static final ThreadLocal<List<String>> EXCEL_CACHE_KEY = new ThreadLocal<>();

    /** 保存所有的key的集合 **/
    private static final ThreadLocal<String> PREFIX_CACHE_KEY = new ThreadLocal<>();

    @PostConstruct
    public void initStaticData() {

    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-08 13:36:56
     * @remark 保存一个租户的key
     */
    public static String createCacheKey(String ... keys){
        if (keys == null || keys.length == 0){
            throw new RuntimeException("key 不能为空");
        }
        String afterPrefix = getThreadAfterFixKey();
        String first = keys[0];
        first = first + ":" + afterPrefix;
        keys[0] = first;
        String key = CacheKeyUtil.createTenantKey(keys);
        List<String> saveKeys = EXCEL_CACHE_KEY.get();
        // 避免重复创建相同的key
        if (ObjectUtil.isNotEmpty(saveKeys) && saveKeys.contains(key)){
            return key;
        }
        if (saveKeys == null) {
            saveKeys = new ArrayList<>();
            EXCEL_CACHE_KEY.set(saveKeys);
        }
        saveKeys.add(key);
        return key;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-08 14:24:14
     * @remark 清空本地的缓存
     */
    public static void clearAllKey(){
        List<String> allKeyList = EXCEL_CACHE_KEY.get();
        if (allKeyList != null){
//            redisTemplate.delete(allKeyList);
            EXCEL_CACHE_KEY.remove();
        }
        PREFIX_CACHE_KEY.remove();
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-08 13:52:16
     * @remark 获取前缀的key
     */
    public static String getThreadAfterFixKey(){
        String preFixKey = PREFIX_CACHE_KEY.get();
        if (preFixKey == null){
            String uuid = UUID.randomUUID().toString().replace("-", "");
            if (uuid.length() > 10){
                uuid = uuid.substring(0, 10);
            }
            preFixKey = uuid;
            PREFIX_CACHE_KEY.set(preFixKey);
        }
        return preFixKey;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-03-19 09:34:47
     * @remark 设置这个线程的key
     */
    public static void setThreadAfterFixKey(String key){
        PREFIX_CACHE_KEY.set(key);
    }

}
