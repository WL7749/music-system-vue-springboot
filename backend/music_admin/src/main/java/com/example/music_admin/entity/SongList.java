package com.example.music_admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 歌单
 * </p>
 *
 * @author jerry
 * @since 2025-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SongList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String pic;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 风格
     */
    private String style;


}
