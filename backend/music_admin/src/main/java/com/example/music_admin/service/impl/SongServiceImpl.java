package com.example.music_admin.service.impl;

import com.example.music_admin.entity.Song;
import com.example.music_admin.mapper.SongMapper;
import com.example.music_admin.service.ISongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌曲 服务实现类
 * </p>
 *
 * @author jerry
 * @since 2025-11-16
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements ISongService {

}
