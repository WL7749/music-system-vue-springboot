package com.example.music_admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.music_admin.entity.Singer;
import com.example.music_admin.service.ISingerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author jerry
 * @since 2025-10-19
 */
@RestController

@RequestMapping("/singer")
public class SingerController {
    private final ISingerService singerService;

    public SingerController(ISingerService singerService) {
        this.singerService = singerService;
    }

    ObjectMapper jsonTool = new ObjectMapper();

    //指定操作条件：根据username进行模糊查询
    //  定义查询的请求接口  http://localhost:8080/music/singer/list

    //根据name进行匹配查询的操作 like模糊查询
// http://localhost:8085/music/singer/list?pageNum=2&size=4
    @RequestMapping("/list")
    public String list(@RequestParam(required = false, defaultValue = "") String name,
                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = false, defaultValue = "10") Integer size) throws JsonProcessingException {
        //1、定义map键值对的集合
        HashMap<String, Object> result = new HashMap();
        //构建条件构造器
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(name != null && !name.equals(""), "name", name);
        //分页对象构建 指定页码和每页显示的记录数
        IPage<Singer> page = new Page<>(pageNum, size);
        //2、使用业务层的page分页查询方法\注入queryWrapper
        IPage<Singer> singerIPage = singerService.page(page, queryWrapper);
        List<Singer> list = singerIPage.getRecords();
        //3、返回json解析的结果【字符串】
        result.put("list", list);
        result.put("total", singerIPage.getTotal());
        return jsonTool.writeValueAsString(result);
    }

    //定义删除的请求接口
    @RequestMapping("delete") //@RequestBody表示解析json格式的数据
    public String delete(Integer id) throws JsonProcessingException {
        //定义一个集合，用于存放健值对结果
        HashMap<String, Object> result =new HashMap<>();
        //查询数据
        boolean delete=singerService.removeById(id);
        result.put("success",delete);
        //返回json数据
        return jsonTool.writeValueAsString(result);
    }

    @RequestMapping("update")
    public String update(@RequestBody Singer singer) throws JsonProcessingException {
        System.out.println("接收到的singer对象：" + singer); // 打印实体，排查字段缺失/类型错误
        HashMap<String, Object> result = new HashMap<>();
        boolean update = singerService.saveOrUpdate(singer);
        result.put("success", update);
        return jsonTool.writeValueAsString(result);
    }
}
