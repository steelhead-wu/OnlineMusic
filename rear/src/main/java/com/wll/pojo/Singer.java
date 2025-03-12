package com.wll.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author wll
 * @since 2025-02-15
 */
@Getter
@Setter
@TableName("singer")
//@ApiModel(value = "Singer对象", description = "")
@Schema(name = "Singer对象", description = "")
public class Singer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("sex")
    private Integer sex;

    @TableField("pic")
    private String pic;

    @TableField("birth")
    private LocalDate birth;

    @TableField("location")
    private String location;

    @TableField("introduction")
    private String introduction;

    @TableField("song_number")
    private String songNumber;

    @TableField("album_number")
    private String albumNumber;
}
