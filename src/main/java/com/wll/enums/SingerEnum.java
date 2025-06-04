package com.wll.enums;

/**
 * @time 2025/3/11 23:42 周二
 */
public enum SingerEnum {
    ALL_SINGER(0),

    FEMALE_SINGER(1),

    MALE_SINGER(2),

    GROUP(3);

    private final int id;
    private final String value;

    SingerEnum(int id) {
        this.id = id;
        value = "singer:" + id;
    }

    public int getId() {
        return id;
    }


    public String getValue() {
        return value;
    }
}
