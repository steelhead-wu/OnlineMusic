package com.wll.pojo.DO;

/**
 * @time 2025/2/24 15:05 周一
 */



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wll.pojo.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class CommentDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField
    private User user;

    @TableField("song_list_id")
    private Integer songListId;

    @TableField("content")
    private String content;

    @TableField("created_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @TableField("up")
    private Integer up;


}
