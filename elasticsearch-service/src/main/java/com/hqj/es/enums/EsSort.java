package com.hqj.es.enums;

/**
 * 排序
 */
public enum EsSort {
    ASC("asc", "升序"),
    DESC("desc", "降序"),
    ;

    private String value;
    private String desc;

    EsSort(String value, String desc) {
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
