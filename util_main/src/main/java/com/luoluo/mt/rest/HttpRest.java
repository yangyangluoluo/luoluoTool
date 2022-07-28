package com.luoluo.mt.rest;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author lijianguo
 * @Date 2022/7/19 13:22
 * 请输入类的简介
 **/
@Log4j2
@Validated
@Api(tags = {"Http测试的接口"})
@RestController
@RequestMapping("/httpTest")
public class HttpRest {

    @Autowired(required = false) private RestTemplate restTemplate;



    String BASE_REGULATION_URL = "http://admin.test.internal.avatr.com:9080/crmcenteryunxi/api/regulation/v1/knowledge/saveUpdate";
    String JSON_BODY = "{\n" +
            "    \"contentList\":[\n" +
            "        {\n" +
            "            \"title\":\"http_ceshi\",\n" +
            "            \"key\":null,\n" +
            "            \"studyHour\":1,\n" +
            "            \"studyMin\":1,\n" +
            "            \"studySec\":1,\n" +
            "            \"theUrl\":\"1\",\n" +
            "            \"introduce\":\"11\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"replaceList\":[\n" +
            "        {\n" +
            "            \"title\":\"http_ceshi\",\n" +
            "            \"theUrl\":\"1\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"termList\":[\n" +
            "\n" +
            "    ],\n" +
            "    \"btId\":\"1306386035986628794\",\n" +
            "    \"openType\":1,\n" +
            "    \"connectType\":1,\n" +
            "    \"compulsory\":1,\n" +
            "    \"pulishType\":1,\n" +
            "    \"name\":\"http图文\",\n" +
            "    \"cId\":\"1307400506581418299\",\n" +
            "    \"keyWords\":\"22_30,29_3\",\n" +
            "    \"sortNum\":1,\n" +
            "    \"imgUrl\":\"https://file.test.avatr.com/salesUser-origin/319c846d-b/009.png\",\n" +
            "    \"KindEditor\":\"11\",\n" +
            "    \"openTypeList\":[\n" +
            "        \"1294187844757525666\",\n" +
            "        \"1311408689269417672\",\n" +
            "        \"1307425387440304133\",\n" +
            "        \"1308562394951564437\",\n" +
            "        \"1307380406631690256\",\n" +
            "        \"1305044241048936536\",\n" +
            "        \"1306048315151169070\",\n" +
            "        \"1306038896897111451\",\n" +
            "        \"1306038702688253324\",\n" +
            "        \"1306021721047119115\",\n" +
            "        \"1305957832257254587\",\n" +
            "        \"1305930938564555899\",\n" +
            "        \"1305930718343672945\",\n" +
            "        \"1305667092942866060\",\n" +
            "        \"1301080835428248619\",\n" +
            "        \"1312664235004897010\",\n" +
            "        \"1312110793660278159\",\n" +
            "        \"1311857295810077798\",\n" +
            "        \"1308878986540196246\",\n" +
            "        \"1308878722786632082\",\n" +
            "        \"1308877737953567115\",\n" +
            "        \"1306311927702828798\",\n" +
            "        \"1306052118779868758\",\n" +
            "        \"1306051991587599946\",\n" +
            "        \"1306051991717623373\",\n" +
            "        \"1306051991818286672\",\n" +
            "        \"1301080870278720561\",\n" +
            "        \"1300976275981293609\",\n" +
            "        \"1300336436177103069\",\n" +
            "        \"1301129033899371801\",\n" +
            "        \"1301057689956365615\",\n" +
            "        \"1300987623652940042\",\n" +
            "        \"1301129082180005158\",\n" +
            "        \"1311113270776800425\",\n" +
            "        \"1300976336838547506\",\n" +
            "        \"1300527822279011461\",\n" +
            "        \"1301129198059187511\",\n" +
            "        \"1311113085745079460\",\n" +
            "        \"1301314939070439523\",\n" +
            "        \"1301041100404012088\",\n" +
            "        \"1304965851991416891\",\n" +
            "        \"1304965619895410739\",\n" +
            "        \"1303938167765012532\",\n" +
            "        \"1301677412858489141\",\n" +
            "        \"1301677796129794362\",\n" +
            "        \"1301677353180883248\",\n" +
            "        \"1301677129438882091\",\n" +
            "        \"1301676958983416102\"\n" +
            "    ],\n" +
            "    \"endTime\":\"2022-07-22T14:23:52.977Z\",\n" +
            "    \"relationList\":[\n" +
            "\n" +
            "    ],\n" +
            "    \"forwardNews\":1,\n" +
            "    \"bmdj\":[\n" +
            "        1,\n" +
            "        2,\n" +
            "        3\n" +
            "    ],\n" +
            "    \"addWatermark\":1,\n" +
            "    \"noCopy\":1,\n" +
            "    \"noShare\":1,\n" +
            "    \"theType\":1,\n" +
            "    \"status\":2,\n" +
            "    \"orgList\":[\n" +
            "        {\n" +
            "            \"orgId\":\"1294187844757525666\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1311408689269417672\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1307425387440304133\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1308562394951564437\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1307380406631690256\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1305044241048936536\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1306048315151169070\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1306038896897111451\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1306038702688253324\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1306021721047119115\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1305957832257254587\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1305930938564555899\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1305930718343672945\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1305667092942866060\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301080835428248619\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1312664235004897010\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1312110793660278159\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1311857295810077798\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1308878986540196246\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1308878722786632082\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1308877737953567115\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1306311927702828798\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1306052118779868758\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1306051991587599946\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1306051991717623373\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1306051991818286672\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301080870278720561\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1300976275981293609\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1300336436177103069\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301129033899371801\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301057689956365615\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1300987623652940042\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301129082180005158\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1311113270776800425\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1300976336838547506\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1300527822279011461\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301129198059187511\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1311113085745079460\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301314939070439523\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301041100404012088\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1304965851991416891\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1304965619895410739\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1303938167765012532\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301677412858489141\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301677796129794362\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301677353180883248\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301677129438882091\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"orgId\":\"1301676958983416102\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";



//    @PostMapping("/test1")
//    @ApiOperation(value = "生存测试的信息")
//    @PostConstruct
    public String test1() throws IOException {

        JSONObject jsonObject = JSONObject.parseObject(JSON_BODY);
        Map itemMap = JSONObject.toJavaObject(jsonObject, Map.class);

//        String result = restTemplate.postForObject(BASE_REGULATION_URL, jsonObject, String.class);

        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("api-version", "1.0");
        requestHeaders.add("request_person_a", "1301041746391839848");
        requestHeaders.add("login-token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib3NzIiwiYWNjb3VudElkIjoiemhvdWVyIiwiZXhwIjoxNjU4Mjk2MTMxfQ.VD_oXvh7oB2UNbCZWlvGvrLxpWeRgguBCrWyWNBixGs");
        //HttpEntity
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(itemMap, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        //post
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);
//        log.info("=== {}", responseEntity);

        AtomicInteger test = new AtomicInteger();
        test.set(56033);


        new Thread(() -> {
            for (int  i = 0; i < 100000; i++){
                String theName = "自动测试==" + test.getAndIncrement();
                itemMap.put("name", theName);
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);
                log.info("===  {} {}", theName, responseEntity);
            }
        }).start();

        new Thread(() -> {
            for (int  i = 0; i < 100000; i++){
                String theName = "自动测试==" + test.getAndIncrement();;
                itemMap.put("name", theName);
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);
                log.info("===  {} {}", theName, responseEntity);
            }
        }).start();

        new Thread(() -> {
            for (int  i = 0; i < 100000; i++){
                String theName = "自动测试==" + test.getAndIncrement();;
                itemMap.put("name", theName);
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);
                log.info("===  {} {}", theName, responseEntity);
            }
        }).start();

        new Thread(() -> {
            for (int  i = 0; i < 100000; i++){
                String theName = "自动测试==" + test.getAndIncrement();;
                itemMap.put("name", theName);
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);
                log.info("===  {} {}", theName, responseEntity);
            }
        }).start();

        for (int  i = 1000; i < 100000; i++){
            String theName = "自动测试==" + test.getAndIncrement();
            itemMap.put("name", theName);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);
            log.info("===  {} {}", theName, responseEntity);
//            ThreadUtil.sleep(10);
        }

//        ExecutorService es = Executors.newFixedThreadPool(4);
//        for (int i = 5684; i < 100000; i++) {
//            int finalI = i;
//            es.submit(new Runnable() {
//                @Override
//                public void run() {
//                    String theName = "自动测试的名字是第几个..." + finalI;
//                    itemMap.put("name", theName);
//                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);
//                    log.info("===  {} {}", theName, responseEntity);
//                    ThreadUtil.sleep(10);
//                }
//            });
//        }





        return "test1...";



    }





}
