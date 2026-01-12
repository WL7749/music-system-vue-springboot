package com.example.music_admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.music_admin.entity.Song;
import com.example.music_admin.service.ISongService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/song")
public class SongController {


    //业务层的对象
    @Autowired
    ISongService songService;

    //json的解析工具
    ObjectMapper jsonTool = new ObjectMapper();

    //1、根据歌曲id查询歌曲  http://localhost:8085/music/song/detail?id=2
    @RequestMapping("/detail")
    public String detail(Integer id) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //调用了业务层的查询
        Song song = songService.getById(id);
        // key - value
        result.put("song", song);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

}