package com.luoluo.mt.rest;

import com.alibaba.fastjson.JSONObject;
import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
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
 * @Date 2022/7/19 13:22
 * 请输入类的简介
 **/
@Log4j2
@Validated
@Api(tags = {"任务详情的接口"})
@RestController
@RequestMapping("/taskDetail")
public class TaskDetailRest {

    @Autowired(required = false) private RestTemplate restTemplate;


    String BASE_REGULATION_URL = "http://admin.test.internal.avatr.com:9080/crmcenteryunxi/api/commission/v1/task/save";
    String JSON_BODY = "{\n" +
            "    \"taskUserType\":1,\n" +
            "    \"isLink\":0,\n" +
            "    \"isAralm\":0,\n" +
            "    \"businessId\":\"1300318968187619330\",\n" +
            "    \"taskId\":\"1300319032224155651\",\n" +
            "    \"taskName\":\"任务测试\",\n" +
            "    \"taskRemark\":\"任务自动生成\",\n" +
            "    \"taskDetailEoList\":[\n" +
            "        {\n" +
            "            \"handleUser\":\"1301041746391839848\",\n" +
            "            \"handleName\":\"周二\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"timeType\":0,\n" +
            "    \"isNotify\":0,\n" +
            "    \"alarmOneTime\":\"14:28\",\n" +
            "    \"startDate\":\"\",\n" +
            "    \"endDate\":\"\",\n" +
            "    \"businessTypeName\":\"销售1级分类\",\n" +
            "    \"taskTypeName\":\"销售\",\n" +
            "    \"quotaName\":\"\",\n" +
            "    \"orgId\":\"1313203220735389729\",\n" +
            "    \"orgName\":\"佣金三店\",\n" +
            "    \"createPerson\":\"周二\"\n" +
            "}";

    // @PostConstruct
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
        test.set(66571);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i= 0; i < 10; i++) {
            executorService.execute(() -> {
                while (test.get() < 100000) {
                    String theName = "任务测试==" + test.getAndIncrement();
                    itemMap.put("taskName", theName);
                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);
                    log.info("===  {} {}", theName, responseEntity);
                }
            });
        }

        return "test1...";
    }





}
