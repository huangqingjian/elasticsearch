package com.hqj.es.enums;

/**
 * 分词
 */
public enum AnalyzerEnum {
    STANDARD("standard"),
    IK_MAX_WORD("ik_max_word"),
    IK_SMART("ik_smart"),
    ;
     private String value;

     AnalyzerEnum(String value) {
         this.value = value;
     }

     public String getValue() {
         return value;
     }
}
