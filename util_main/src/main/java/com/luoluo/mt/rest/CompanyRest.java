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
import org.springframework.web.bind.annotation.PostMapping;
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
public class CompanyRest {

    @Autowired(required = false) private RestTemplate restTemplate;

    String BASE_REGULATION_URL = "http://admin.test.internal.avatr.com:9080/crmcenteryunxi/api/shop/v1/company/add";
//    String BASE_REGULATION_URL = "http://127.0.0.1:8084/v1/company/add";
    String JSON_BODY = "{\n" +
            "    \"type\":1,\n" +
            "    \"name\":\"测试的公司01\",\n" +
            "    \"creditCode\":\"D5345623DXCF34FC6G\",\n" +
            "    \"merchantNumber\":\"1111\",\n" +
            "    \"openBank\":\"中国银行\",\n" +
            "    \"bankAccount\":\"99999977777\",\n" +
            "    \"eContractTemplateId\":\"909012121\",\n" +
            "    \"status\":1,\n" +
            "    \"id\":null,\n" +
            "    \"provinceName\":\"安徽省\",\n" +
            "    \"provinceCode\":\"22075\",\n" +
            "    \"cityCode\":\"23674\",\n" +
            "    \"cityName\":\"合肥市\",\n" +
            "    \"countyCode\":\"23752\",\n" +
            "    \"countyName\":\"包河区\",\n" +
            "    \"organizationId\":\"\",\n" +
            "    \"dr\":\"0\"\n" +
            "}";

//    @PostConstruct
    @PostMapping("/test1")
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
        test.set(3866);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i= 0; i < 10; i++) {
            executorService.execute(() -> {
                while (test.get() < 100000) {
                    String theName = "测试的公司==" + test.getAndIncrement();
                    itemMap.put("name", theName);
                    itemMap.put("merchantNumber", "1111110"+test.get());
                    itemMap.put("eContractTemplateId", "1111110"+test.get());
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

//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_REGULATION_URL, requestEntity, String.class);

        return "test1...";
    }

}
