package com.example.music_admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    private final ObjectMapper jsonTool = new ObjectMapper();

    // 统一根路径
    private static final String BASE = "E:/music-server/";

    // ----------- 公共上传方法 -----------
    private HashMap<String, Object> upload(MultipartFile file, String folder) {
        HashMap<String, Object> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("success", false);
            result.put("msg", "文件为空");
            return result;
        }

        String saveDir = BASE + folder + "/";
        File dir = new File(saveDir);
        if (!dir.exists()) dir.mkdirs();

        // 生成新文件名
        String oldName = file.getOriginalFilename();
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        String newName = UUID.randomUUID().toString().replace("-", "") + suffix;

        File dest = new File(saveDir + newName);

        try {
            file.transferTo(dest);

            // ★存数据库的路径（必须以 /music/ 开头）
            result.put("success", true);
            result.put("path", "/music/" + folder + "/" + newName);
            result.put("msg", "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "上传失败");
        }

        return result;
    }


    // ----------- 1. 用户头像 -----------
    @PostMapping("/avatar/upload")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) throws Exception {
        return jsonTool.writeValueAsString(upload(file, "avatorImages"));
    }

    // ----------- 2. 歌手图片 -----------
    @PostMapping("/singerPic/upload")
    public String uploadSingerPic(@RequestParam("file") MultipartFile file) throws Exception {
        return jsonTool.writeValueAsString(upload(file, "img/singerPic"));
    }

    // ----------- 3. 歌单图片 -----------
    @PostMapping("/songListPic/upload")
    public String uploadSongListPic(@RequestParam("file") MultipartFile file) throws Exception {
        return jsonTool.writeValueAsString(upload(file, "img/songListPic"));
    }

    // ----------- 4. 歌曲封面图 -----------
    @PostMapping("/songPic/upload")
    public String uploadSongPic(@RequestParam("file") MultipartFile file) throws Exception {
        return jsonTool.writeValueAsString(upload(file, "img/songPic"));
    }

    // ----------- 5. 歌曲文件 mp3 -----------
    @PostMapping("/song/upload")
    public String uploadSongFile(@RequestParam("file") MultipartFile file) throws Exception {
        return jsonTool.writeValueAsString(upload(file, "song"));
    }

}