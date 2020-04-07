package com.hqj.es.enums;

import org.springframework.util.StringUtils;

/**
 * 数据类型
 */
public enum DataType {
    TEXT("text", 1),
    KEYWORD("keyword", 0),
    INTEGER("integer", 0),
    LONG("long", 0),
    FLOAT("float", 0),
    DOUBLE("double", 0),
    BOOLEAN("boolean", 0),
    DATE("date", 0),
    ;

    private String value;
    private Integer anayzed;


    DataType(String value, Integer anayzed) {
        this.value = value;
        this.anayzed = anayzed;
    }

    public static Integer getDataTypeAnayzed(String value) {
        if(StringUtils.isEmpty(value)) {
            return 0;
        }
        for(DataType dt : DataType.values()) {
            if(dt.getValue().equals(value)) {
                return dt.getAnayzed();
            }
        }
        return 0;
    }

    public String getValue() {
        return value;
    }

    public Integer getAnayzed() {
        return anayzed;
    }
}
