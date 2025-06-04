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
 * @since 2025-03-05
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@TableName("likes")
//@ApiModel(value = "Likes对象", description = "")
@Schema(name = "Likes对象", description = "")
public class Likes implements Serializable {

    private static final long serialVersionUID = 1L;

    public Likes(Long userId, Integer songId) {
        this.userId = userId;
        this.songId = songId;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Long userId;

    @TableField("song_id")
    private Integer songId;


}
