package com.example.music_admin.service;

import com.example.music_admin.entity.MusicRank;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 歌单评分 服务类
 * </p>
 *
 * @author jerry
 * @since 2025-11-28
 */
public interface IMusicRankService extends IService<MusicRank> {

    public int getScore(Integer songListId);
}
