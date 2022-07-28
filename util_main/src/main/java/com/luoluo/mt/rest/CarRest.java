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
public class CarRest {

    @Autowired(required = false) private RestTemplate restTemplate;

    String BASE_REGULATION_URL = "http://admin.test.internal.avatr.com:9080/crmcenteryunxi/api/shop/v1/shop/assets/car/add";
//    String BASE_REGULATION_URL = "http://127.0.0.1:8084/v1/company/add";
    String JSON_BODY = "{\n" +
        "    \"businessType\":\"1\",\n" +
        "    \"carSeriesId\":1,\n" +
        "    \"carModelName\":\"2021款 静领行\",\n" +
        "    \"bodyColor\":\"白色\",\n" +
        "    \"interiorColor\":\"银色\",\n" +
        "    \"factoryGuidePrice\":\"128\",\n" +
        "    \"carNo\":\"123\",\n" +
        "    \"upNoTime\":\"2022-07-21T06:00:16.770Z\",\n" +
        "    \"vehicleNo\":\"12345123451234512\",\n" +
        "    \"engineCode\":\"121212\",\n" +
        "    \"carPassCart\":\"12121\",\n" +
        "    \"shopName\":\"佣金三店\",\n" +
        "    \"attendedStartTime\":\"2022-07-21T06:00:45.053Z\",\n" +
        "    \"attendedEndTime\":\"2022-07-22T06:00:47.169Z\",\n" +
        "    \"inRepertoryTime\":\"2022-07-22T06:00:50.020Z\",\n" +
        "    \"carStatus\":\"1\",\n" +
        "    \"insertType\":\"2\",\n" +
        "    \"retireStatus\":\"1\",\n" +
        "    \"shopId\":\"1313203220568670210\",\n" +
        "    \"extFields\":{\n" +
        "\n" +
        "    },\n" +
        "    \"carModelId\":10,\n" +
        "    \"carSeriesName\":\"领界EV 1\",\n" +
        "    \"inShopTime\":\"\",\n" +
        "    \"upShopTime\":\"\"\n" +
        "}";

//    @PostConstruct
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
        test.set(8010);

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
