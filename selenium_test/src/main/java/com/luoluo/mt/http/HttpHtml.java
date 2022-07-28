package com.luoluo.mt.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * @author lijianguo
 * @Date 2022/7/18 22:15
 * 请输入类的简介
 **/
@Log4j2
public class HttpHtml {

    @Autowired private RestTemplate restTemplate;

    public static void main(String[] args) {

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

        JSONObject jsonObject = JSONObject.parseObject(JSON_BODY);
        Map<String, Object> itemMap = JSONObject.toJavaObject(jsonObject, Map.class);
//        HttpRequest.head();

        String result = HttpUtil.post(BASE_REGULATION_URL, itemMap);
        log.info("=== {}", result);
        log.info("===");


    }
}
