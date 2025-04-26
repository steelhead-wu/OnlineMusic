package com.wll.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
 * @since 2025-01-20
 */
@Data
@TableName("user")
//@ApiModel(value = "User对象", description = "")
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User对象", description = "")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public User(String account, Long id) {
        this.id = id;
        this.account = account;
    }

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("id")
    private Long id;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("nickname")
    private String nickname;

    @TableField("avatar")
    private String avatar;

    @TableField("birth")
    private LocalDate birth;

    @TableField("whats_up")
    private String whatsUp;

    @TableField("sex")
    private Integer sex;


}
