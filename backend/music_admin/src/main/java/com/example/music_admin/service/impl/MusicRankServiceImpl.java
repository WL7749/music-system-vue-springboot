package com.example.music_admin.service.impl;

import com.example.music_admin.entity.MusicRank;
import com.example.music_admin.mapper.MusicRankMapper;
import com.example.music_admin.service.IMusicRankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌单评分 服务实现类
 * </p>
 *
 * @author jerry
 * @since 2025-11-28
 */
@Service
public class MusicRankServiceImpl extends ServiceImpl<MusicRankMapper, MusicRank> implements IMusicRankService {

    //依赖mapper
    @Autowired
    MusicRankMapper musicRankMapper;
    @Override
    public int getScore(Integer songListId) {
        return musicRankMapper.getScore(songListId);
    }
}
