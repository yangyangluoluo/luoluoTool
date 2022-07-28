package com.luoluo.mt.rest;

import com.alibaba.fastjson.JSONObject;
import com.luoluo.mt.domain.RestData;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lijianguo
 * @Date 2022/7/20 15:56
 * 请输入类的简介
 **/
@Log4j2
@Validated
@Api(tags = {"Http测试的接口"})
@RestController
@RequestMapping("/httpTest")
public class CoCreateRest {

    @Autowired(required = false) private RestTemplate restTemplate;


    String BASE_REGULATION_URL = "http://admin.test.internal.avatr.com:9080/crmcenteryunxi/api/regulation/v1/coCreate/save";
    String JSON_BODY = "{\n" +
            "    \"img\":\"C:\\\\fakepath\\\\001.png\",\n" +
            "    \"name\":\"共创测试==\",\n" +
            "    \"btId\":\"1300974392927589652\",\n" +
            "    \"cId\":\"1300992410176105878\",\n" +
            "    \"vvGood\":1,\n" +
            "    \"vgood\":1,\n" +
            "    \"good\":1,\n" +
            "    \"status\":3,\n" +
            "    \"remark\":\"11\",\n" +
            "    \"theImg\":\"https://file.test.avatr.com/salesUser-origin/f548d4c9-6/001.png\",\n" +
            "    \"startTime\":\"2022-07-24 00:00:00\",\n" +
            "    \"endTime\":\"2022-07-25 23:59:59\"\n" +
            "}";

//    @PostConstruct
    public String test1() throws IOException {

        JSONObject jsonObject = JSONObject.parseObject(JSON_BODY);
        Map itemMap = JSONObject.toJavaObject(jsonObject, Map.class);
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("api-version", "1.0");
        requestHeaders.add("request_person_a", "1301041746391839848");
        requestHeaders.add("login-token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib3NzIiwiYWNjb3VudElkIjoiemhvdWVyIiwiZXhwIjoxNjU4Mjk2MTMxfQ.VD_oXvh7oB2UNbCZWlvGvrLxpWeRgguBCrWyWNBixGs");
        //HttpEntity
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(itemMap, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();

        AtomicInteger test = new AtomicInteger();
        test.set(94010);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i= 0; i < 40; i++) {
            executorService.execute(() -> {
                while (test.get() < 100000) {
                    String theName = "共创测试==" + test.getAndIncrement();
                    itemMap.put("name", theName);
                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);
                    RestData restData = JSONObject.parseObject(responseEntity.toString(), RestData.class);
                    log.info("===  {} {}", theName, responseEntity);
                }
            });
        }

        return "test1...";
    }

}
