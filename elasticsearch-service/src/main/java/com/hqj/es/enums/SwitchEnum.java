package com.hqj.es.enums;

public enum SwitchEnum {
    ON(1, "开"),
    OFF(0, "关"),
    ;

    private int value;
    private String desc;

    SwitchEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getSwitchDesc(Integer value) {
        if(value == null) {
            return null;
        }
        SwitchEnum[] switchs = SwitchEnum.values();
        for(SwitchEnum s : switchs) {
            if(s.getValue() == value) {
                return s.getDesc();
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
