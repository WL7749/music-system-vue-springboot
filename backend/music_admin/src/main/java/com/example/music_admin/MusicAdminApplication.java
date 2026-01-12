package com.example.music_admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.music_admin.mapper")
@SpringBootApplication
public class MusicAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicAdminApplication.class, args);
    }

}
