package com.example.music_admin.service.impl;

import com.example.music_admin.entity.ListSong;
import com.example.music_admin.mapper.ListSongMapper;
import com.example.music_admin.service.IListSongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌单-歌曲-中间表 服务实现类
 * </p>
 *
 * @author jerry
 * @since 2025-11-26
 */
@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements IListSongService {

}
