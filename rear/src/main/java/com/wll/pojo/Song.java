package com.wll.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author wll
 * @since 2025-01-23
 */
@Getter
@Setter
@TableName("song")
@ApiModel(value = "Song对象", description = "")
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("singer_id")
    private Integer singerId;

    @TableField("title")
    private String title;

    @TableField("album")
    private String album;

    @TableField("picture")
    private String picture;

    @TableField("lyric")
    private String lyric;

    @TableField("url")
    private String url;


}
