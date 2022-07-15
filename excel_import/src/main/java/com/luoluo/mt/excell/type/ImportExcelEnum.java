package com.luoluo.mt.excell.type;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ImportExcelEnum {

    /** 导入试驾的车辆  **/
    TRY_APPLY_CAR("导入试驾的车辆"),

    /** 导入展厅的车辆  **/
    TRY_SHOW_CAR("导入展厅的车辆"),

    /** 导入店铺  **/
    SHOP_IMPORT("导入店铺"),

    /** 导入公司  **/
    COMPANY_IMPORT("导入公司"),

    /** 导入考勤  **/
    KQ_IMPORT("导入考勤"),

    /** 导入排班  **/
    IN_PAI_BAN("导入排班"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static ImportExcelEnum match(String val, ImportExcelEnum def) {
        for (ImportExcelEnum enm : ImportExcelEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ImportExcelEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ImportExcelEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @ApiModelProperty(value = "编码")
    public String getCode() {
        return this.name();
    }
}
