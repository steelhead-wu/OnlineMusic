package com.wll.enums;

/**
 * @time 2025/3/12 22:59 周三
 */
public enum LLM {
    DEEP_SEEK(System.getenv("DEEPSEEK_AI_API_KEY"),
            "https://api.deepseek.com");


    private final String api_key;
    private final String base_url;

    LLM(String api_key, String base_url) {
        this.api_key = api_key;
        this.base_url = base_url;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getBase_url() {
        return base_url;
    }
}
