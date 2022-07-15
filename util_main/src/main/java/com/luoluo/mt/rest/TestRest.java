package com.luoluo.mt.rest;

import com.luoluo.mt.excell.ExcelDataTypeEnum;
import com.luoluo.mt.excell.ExcelImportValid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    String test1(@RequestPart(value = "file") MultipartFile file) throws IOException {

        // 导入数据
        Workbook wb = WorkbookFactory.create(file.getInputStream());
        ExcelImportValid<RuleCheckEx> excelImportValid = new ExcelImportValid(wb, 1);
        excelImportValid.addRuleClass(RuleCheckEx.class);
        List<RuleCheckEx> sucData = excelImportValid.getCellDataToArray(ExcelDataTypeEnum.SUC, RuleCheckEx.class);
        List<RuleCheckEx> failData = excelImportValid.getCellDataToArray(ExcelDataTypeEnum.FAIL, RuleCheckEx.class);
        // 把失败的数据删除
        excelImportValid.clearRowAndSetStyle(ExcelDataTypeEnum.SUC);

        return "test1......";
    }

}
