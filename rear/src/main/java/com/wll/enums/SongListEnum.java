package com.wll.enums;

/**
 * @time 2025/3/11 22:00 周二
 */
public enum SongListEnum {
    ALL_SONG_LIST("全部", 0),
    CN("华语", 1),
    EUR_AM("欧美", 2),
    JP_KO("日韩", 3),
    CANTONESE("粤语", 4),
    BGM("BGM", 5),
    LIGHT_MUSIC("轻音乐", 6),
    INSTRUMENTS("乐器", 7);


    private int id;
    private final String value;
    private final String originalValue;

    SongListEnum(String value, int id) {
        this.originalValue = value;
        this.id = id;
        this.value = "songList:" + value;
    }


    public String getOriginalValue() {
        return originalValue;
    }

    public String getValue() {
        return value;
    }


    public int getId() {
        return id;
    }
}
