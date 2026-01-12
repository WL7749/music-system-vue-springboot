package com.example.music_admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//本地文件的映射    虚拟url映射本地的文件路径
@Configuration
public class LocalResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/music/avatorImages/**")
                .addResourceLocations("file:E:/music-server/avatorImages/");

        registry.addResourceHandler("/music/img/singerPic/**")
                .addResourceLocations("file:E:/music-server/img/singerPic/");

        registry.addResourceHandler("/music/img/songPic/**")
                .addResourceLocations("file:E:/music-server/img/songPic/");

        registry.addResourceHandler("/music/img/songListPic/**")
                .addResourceLocations("file:E:/music-server/img/songListPic/");

        registry.addResourceHandler("/music/song/**")
                .addResourceLocations("file:E:/music-server/song/");
    }
}