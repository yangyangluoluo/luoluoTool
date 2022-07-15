package com.luoluo.mt.excell;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo-- List<List<ValidResult>>二维数组代表所有的验证结果，一个List<ValidResult> 代表某行 row 的验证xx, 一个ValidResult 代表一个xx的xx
 * @time: 2020/9/30 8:57
 * @remark: 验证的结果
 */
@Data
@Accessors(chain = true)
public class ValidResult {

    @ApiModelProperty(value = "数据所在的行")
    private int row;

    @ApiModelProperty(value = "数据所在的开始的列号")
    private int col;

    @ApiModelProperty(value = "要验证的列的数目默认1列 验证1列")
    private int colSum;

    @ApiModelProperty(value = "要验证所有的列的数据")
    private List<String> colDataList;

    @ApiModelProperty(value = "0错误（false） 1正确(true)")
    private Boolean status = true;

    @ApiModelProperty(value = "验证错误的描述")
    private String invalidDesc;

    @ApiModelProperty(value = "验证错误的描述的多个位置")
    private List<IdxDesc> IdDescList = new ArrayList<>();

    /**
     * @author lijianguo
     * @Date 2022/6/11 11:00
     *
     **/
    public void addFailDesc(Integer idx, String msg){
        setStatus(false);
        if (IdDescList == null){
            IdDescList = new ArrayList();
        }
        IdxDesc idxDesc = null;
        for (IdxDesc data : IdDescList){
            if (ObjectUtil.equal(idx, data.descIdx)){
                idxDesc = data;
                break;
            }
        }
        if (idxDesc == null) {
            idxDesc  = new IdxDesc();
        }
        idxDesc.setDescIdx(idx);
        idxDesc.addFailInfo(msg);
        IdDescList.add(idxDesc);
    }
    /**
     * @author lijianguo
     * @Date 2022/6/11 10:22
     * 请输入方法的简介
     **/
    @Data
    public static class IdxDesc{

        @ApiModelProperty(value = "验证错误的描述")
        private String invalidDesc;

        @ApiModelProperty(value = "验证错误的描述的位置")
        private Integer descIdx;

        /**
         * @author lijianguo
         * @Date 2022/6/11 12:13
         * 添加
         **/
        public void addFailInfo(String str){
            if (StrUtil.isBlank(invalidDesc)){
                invalidDesc = str;
            }else {
                invalidDesc = invalidDesc + ";"+str;
            }
        }
    }
}
