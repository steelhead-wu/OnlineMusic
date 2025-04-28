package com.wll.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wll.pojo.Song;
import lombok.*;

/**
 * @time 2025/4/28 20:34 周一
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDO extends Song {
    @TableField("singer_name")
    private String singerName;
}
