package com.wll.utils;


/**
 * @time 2025/4/24 17:48 周四
 */
public class StringUtils {




    /**
     * check string if exist whitespace
     */
    public static boolean isExistWhiteSpace(String string) {
        if (isEmpty(string)) return false;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isWhitespace(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * check string has all whitespace
     *
     * @return true if string is null or only whitespace else false
     */
    public static boolean isBlank(String string) {
        if (isEmpty(string)) {
            return true;
        }
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * check string is null or empty.
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * check string is not null or empty.
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }
}
