package com.luoluo.mt.selenium;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ThreadUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * @author lijianguo
 * @Date 2022/7/17 16:48
 * 请输入类的简介
 **/
@Log4j2
public class SeleniumApp {


    public static void main(String[] args) throws InterruptedException {


        System.setProperty("webdriver.chrome.driver", "/Users/jianguoli/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        Actions action = new Actions(driver);
        driver.get("http://admin.test.internal.avatr.com:9080/user/login");
        ThreadUtil.sleep(5000);

        WebElement basic_username = driver.findElement(By.id("basic_username"));
        basic_username.sendKeys("zhouer");

        WebElement basic_password = driver.findElement(By.id("basic_password"));
        basic_password.sendKeys("avatr_0002");

        WebElement basic_messageCode = driver.findElement(By.id("basic_messageCode"));
        basic_messageCode.sendKeys("1234");

        List<WebElement> login_btn = driver.findElements(By.xpath("//button[@class='ant-btn ant-btn-primary ant-btn-lg']"));
        login_btn.get(1).click();


        // 这里是新增页面
        ThreadUtil.sleep(5000);
        driver.get("http://admin.test.internal.avatr.com:9080/employeesystem/#/knowledgeBaseMenu/knowledgeBaseMgmt");

        ThreadUtil.sleep(5000);
        List<WebElement> choose_btn_list = driver.findElements(By.xpath("//button[@class='ant-btn ant-dropdown-trigger ant-btn-primary']"));
        action.moveToElement(choose_btn_list.get(0)).perform();


        RegulationHtml.addRegulation(driver, action);


        ThreadUtil.sleep(50000);
        log.info("=== === === ");

    }

    /**
     * @author lijianguo
     * @Date 2022/7/18 18:29
     * 新增知识
     **/

}
