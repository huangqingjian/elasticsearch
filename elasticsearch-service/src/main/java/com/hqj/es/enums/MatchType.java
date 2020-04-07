package com.hqj.es.enums;

/**
 * es匹配方式
 */
public enum MatchType {
    ANALYZE_MATCH("analyze_match", "分词单个匹配"),
    ANALYZE_MULTI_MATCH("analyze_multiMatch", "分词多个匹配"),
    PRECISE_MATCH("precise_match", "精准单个匹配"),
    PRECISE_MULTI_MATCH("precise_multi_match", "精准多个匹配"),
    LIKE_MATCH("like_match", "模糊匹配（字段映射类型为text）"),
    WILDCARD("wildcard", "通配符匹配（字段映射类型为keyword）"),
    ;

    private String value;
    private String desc;

    MatchType(String value, String desc) {
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
