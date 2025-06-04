package com.wll.enums;

/**
 * @time 2025/5/3 20:41 周六
 */
public enum StaticFilePrefixPath {
    STATIC_FILE_PREFIX_PATH(System.getProperty("user.dir").concat("\\src\\main\\resources\\static"));
    private final String path;


    StaticFilePrefixPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
