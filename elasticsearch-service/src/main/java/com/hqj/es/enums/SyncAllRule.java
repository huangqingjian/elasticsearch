package com.hqj.es.enums;

/**
 * 全量同步数据规则
 */
public enum SyncAllRule {
    EVERYDAY(1, "每天一次"),
    EVERYWEEK(2, "每周一次"),
    EVERYMONTH(3, "每月一次"),
    ;
    private int value;
    private String desc;

    SyncAllRule(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getRuleDesc(Integer value) {
        if(value == null) {
            return null;
        }
        SyncAllRule[] rules = SyncAllRule.values();
        for(SyncAllRule rule : rules) {
            if(rule.getValue() == value) {
                return rule.getDesc();
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
