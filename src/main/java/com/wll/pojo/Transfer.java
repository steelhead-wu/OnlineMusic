package com.wll.pojo;

import com.wll.enums.ResourcesPath;
import lombok.Getter;
import lombok.Setter;

/**
 * @time 2025/4/29 13:28 周二
 */

@Setter
@Getter
public class Transfer {
    private int repoID;
    private String path;
    private String mediaType;
    private String contentDisposition;
}
