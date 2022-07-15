package com.luoluo.mt.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijianguo
 * @Date 2022/7/15 15:15
 * 请输入类的简介
 **/

@Log4j2
@Validated
@Api(tags = {"测试的接口"})
@RestController
@RequestMapping("/test")
public class TestRest {

    @GetMapping("/test1")
    @ApiOperation(value = "test1", notes = "test1")
    String test1(){

        return "test1......";
    }

}
