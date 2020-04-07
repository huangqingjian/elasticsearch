package com.hqj.es.enums;

/**
 * 数据同步方式
 */
public enum SyncType {
    SQL(0, "sql"),
    INTERFACE(1, "服务接口"),
    ;
    private int value;
    private String desc;

    SyncType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getSyncTypeDesc(Integer value) {
        if(value == null) {
            return null;
        }
        SyncType[] types = SyncType.values();
        for(SyncType type : types) {
            if(type.getValue() == value) {
                return type.getDesc();
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
