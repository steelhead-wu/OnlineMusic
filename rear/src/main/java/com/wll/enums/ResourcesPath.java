package com.wll.enums;

/**
 * @time 2025/2/25 19:23 周二
 */
public enum ResourcesPath {

    /**
     * System.getProperty("user.dir") returns project path
     */

    USER_AVATAR_PATH(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\asset\\img\\avatarImages", 0),

    COMMENT_PICTURE_PATH(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\asset\\commentPicture", 1);

    private final String path;
    private final int type;

    ResourcesPath(String path, int type) {
        this.path = path;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.path;
    }
}


