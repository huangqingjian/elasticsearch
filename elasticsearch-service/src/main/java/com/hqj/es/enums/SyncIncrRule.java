package com.hqj.es.enums;

/**
 * 增量同步数据规则
 */
public enum SyncIncrRule {
    FIVEMINUTES(1, "5分钟一次"),
    THIRTYMINUTES(2, "30分钟一次"),
    SIXTYMINUTES(3, "60分钟一次"),
    ;
    private int value;
    private String desc;

    SyncIncrRule(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getRuleDesc(Integer value) {
        if(value == null) {
            return null;
        }
        SyncIncrRule[] rules = SyncIncrRule.values();
        for(SyncIncrRule rule : rules) {
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
