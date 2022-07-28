package com.luoluo.mt.selenium;

import cn.hutool.core.thread.ThreadUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * @author lijianguo
 * @Date 2022/7/18 18:29
 * 请输入类的简介
 **/
public class RegulationHtml {

    public static void addRegulation(WebDriver driver,  Actions action){

        ThreadUtil.sleep(5000);
        driver.get("http://admin.test.internal.avatr.com:9080/employeesystem/#/knowledgeBaseMenu/knowledgeBaseMgmt/operationKnowlege");

        WebElement name = driver.findElement(By.id("name"));
        name.sendKeys("name");



        ThreadUtil.sleep(10000);


    }
}
