package com.hqj.es.enums;

/**
 * es匹配方式
 */
public enum MatchMethod {
    AND("and", "and"),
    OR("or", "or"),
    ;

    private String value;
    private String desc;

    MatchMethod(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
