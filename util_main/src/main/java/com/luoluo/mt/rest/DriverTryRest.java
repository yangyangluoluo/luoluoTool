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
@Api(tags = {"公司测试的接口"})
@RestController
@RequestMapping("/companyRest")
public class DriverTryRest {

    @Autowired(required = false) private RestTemplate restTemplate;

    String BASE_REGULATION_URL = "http://admin.test.internal.avatr.com:9080/crmcenteryunxi/api/market/v1/driveTry/add";
//    String BASE_REGULATION_URL = "http://127.0.0.1:8084/v1/company/add";
    String JSON_BODY = "{\n" +
        "    \"signStatus\":-1,\n" +
        "    \"id\":0,\n" +
        "    \"docCreateTime\":\"2022-07-21 16:01:46\",\n" +
        "    \"docTryTime\":\"\",\n" +
        "    \"docSellName\":\"周二\",\n" +
        "    \"docSellId\":142,\n" +
        "    \"docShopName\":\"佣金三店\",\n" +
        "    \"docShopId\":\"1313203220568670210\",\n" +
        "    \"infoTime\":\"2022-07-21 08:00:00\",\n" +
        "    \"infoCityName\":\"\",\n" +
        "    \"infoCityCode\":\"\",\n" +
        "    \"infoCar\":\"\",\n" +
        "    \"infoCarId\":\"\",\n" +
        "    \"infoActivity\":\"\",\n" +
        "    \"infoChannel\":\"\",\n" +
        "    \"infoName\":\"\",\n" +
        "    \"infoPhone\":\"\",\n" +
        "    \"cusName\":\"顾芊芊\",\n" +
        "    \"cusSex\":\"男\",\n" +
        "    \"cusPhone\":\"13699014828\",\n" +
        "    \"detailToType\":1,\n" +
        "    \"detailRouteId\":\"1310575023559686164\",\n" +
        "    \"detailRouteName\":\"路线3\",\n" +
        "    \"detailToTime\":\"\",\n" +
        "    \"detailToAddr\":\"\",\n" +
        "    \"detailDriverId\":\"1301041746192610406\",\n" +
        "    \"detailDriverName\":\"周二\",\n" +
        "    \"detailDriverImg\":\"https://file.test.avatr.com/salesUser-origin/9ed14e1b-6/001.png\",\n" +
        "    \"detailProtocolType\":2,\n" +
        "    \"detailIdCard\":[\n" +
        "\n" +
        "    ],\n" +
        "    \"detailCarId\":10020,\n" +
        "    \"detailCarName\":\"阿维塔 11\",\n" +
        "    \"detailProtocolImg\":[\n" +
        "        \"https://file.test.avatr.com/salesUser-origin/30c61f1e-d/001.png\"\n" +
        "    ],\n" +
        "    \"detailCarNo\":\"\",\n" +
        "    \"detailUpTime\":\"\",\n" +
        "    \"extension\":\"510704200204303522\",\n" +
        "    \"updateTime\":\"2022-07-21 16:03:36\",\n" +
        "    \"detailSj\":1,\n" +
        "    \"detailSc\":1\n" +
        "}";

    @PostConstruct
    public String test1() throws IOException {

        JSONObject jsonObject = JSONObject.parseObject(JSON_BODY);
        Map itemMap = JSONObject.toJavaObject(jsonObject, Map.class);
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("api-version", "1.0");
        requestHeaders.add("request_person_a", "1301041746391839848");
        requestHeaders.add("login-token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib3NzIiwiYWNjb3VudElkIjoiemhvdWVyIiwiZXhwIjoxNjU4NDU4NjY5fQ.QJbsmOxIlF30vWhseVvUe6fBi-4MHvIC1a1mHuA0lU0");
        //HttpEntity
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(itemMap, requestHeaders);

        AtomicInteger test = new AtomicInteger();
        test.set(61997);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i= 0; i < 10; i++) {
            executorService.execute(() -> {
                while (test.get() < 100000) {
                    String theName = "测试"+test.getAndIncrement() + "";
                    itemMap.put("carNo", theName);
                    try {
                        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);
                        RestData restData = JSONObject.parseObject(responseEntity.toString(), RestData.class);
                        log.info("===  {} {}", theName, responseEntity);
                    }catch (Exception e){
                        log.error(e);
                    }
                }
            });
        }
        return "test1...";
    }

}
