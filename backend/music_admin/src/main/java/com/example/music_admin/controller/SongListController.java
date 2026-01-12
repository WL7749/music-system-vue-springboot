package com.example.music_admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.music_admin.entity.ListSong;
import com.example.music_admin.entity.SongList;
import com.example.music_admin.service.IListSongService;
import com.example.music_admin.service.ISongListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/songList")
public class SongListController {
    @Autowired
    IListSongService listSongService;

    //查询歌单的请求    http://localhost:8085/music/song-list/detail?songListId=${songListId}
    @RequestMapping("/detail")
    public String detail(Integer songListId) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //条件
        QueryWrapper<ListSong> wrapper = new QueryWrapper<>();
        //根据歌单id查询
        wrapper.eq("song_list_id", songListId);
        //查询
        List<ListSong> list = listSongService.list(wrapper);
        // key - value
        result.put("list", list);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //业务层的对象
    @Autowired
    ISongListService songListService;

    //json的解析工具
    ObjectMapper jsonTool = new ObjectMapper();

    //查询歌单的请求    http://localhost:8085/music/songList/list
    @RequestMapping("/list")
    public String query() throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //查询所有
        List<SongList> list = songListService.list();
        // key - value
        result.put("list", list);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

}
