package com.example.music_admin.mapper;

import com.example.music_admin.entity.MusicRank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 歌单评分 Mapper 接口
 * </p>
 *
 * @author jerry
 * @since 2025-11-28
 */
public interface MusicRankMapper extends BaseMapper<MusicRank> {

    @Select("select avg(score) from music_rank group by songListId having songListId=#{songListId};")
    public Integer getScore(@Param("songListId") Integer songListId);
}
