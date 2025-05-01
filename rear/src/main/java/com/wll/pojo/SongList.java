package com.wll.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author wll
 * @since 2025-02-18
 */
@Getter
@Setter
@TableName("song_list")
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ApiModel(value = "SongList对象", description = "")
@Schema(name = "SongList对象", description = "")
public class SongList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("pic")
    private String pic;

    @TableField("introduction")
    private String introduction;

    @TableField("style")
    private String style;

    @TableField("rating")
    private Integer rating;

    @TableField("delete_flag")
    private Boolean deleteFlag;
}
