package com.example.music_admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.music_admin.entity.Consumer;
import com.example.music_admin.service.IConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
@RequestMapping("/consumer")
public class ConsumerController {
    private final IConsumerService consumerService;

    public ConsumerController(IConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    ObjectMapper jsonTool = new ObjectMapper();


    //定义一个登录查询用户的请求方法
    @RequestMapping("/login/status")
    public String login(String username, String password) throws JsonProcessingException {
        //响应json格式的数据
        HashMap result = new HashMap();
        //构建条件对象
        QueryWrapper<Consumer> wrapper = new QueryWrapper<>();
        //条件：登录条件
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        //查询操作
        Consumer consumer = consumerService.getOne(wrapper);
        //逻辑判断
        if (consumer != null) {//登录成功
            result.put("code", 1);
            result.put("userMsg", consumer);
        } else {//登录失败
            result.put("code", -1);
        }
        //返回json格式的String数据
        return jsonTool.writeValueAsString(result);
    }

    //指定操作条件：根据username进行模糊查询
    //  定义查询的请求接口  http://localhost:8080/music/consumer/list

    @RequestMapping("/list")
    public String query(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "4") Integer size
    ) throws JsonProcessingException {
        //map集合【键值对】
        HashMap<String, Object> result = new HashMap<>();
        //构建查询条件
        QueryWrapper<Consumer> wrapper = new QueryWrapper<>();
        //条件：username的like模糊查询
        wrapper.like(name != null && !name.equals(""), "username", name);

        //提供分页对象  页码、每页查询条数
        IPage<Consumer> page = new Page<>(pageNum, size);
        //调用了业务层的list方法查询列表
        IPage<Consumer> consumerIPage = consumerService.page(page, wrapper);
        //从分页的结果对象中获取集合list
        List<Consumer> list = consumerIPage.getRecords();

        // key - value
        result.put("list", list);
        result.put("total", consumerIPage.getTotal());

        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //定义删除的请求接口
    @RequestMapping("delete") //@RequestBody表示解析json格式的数据
    public String delete(Integer id) throws JsonProcessingException {
        //定义一个集合，用于存放健值对结果
        HashMap<String, Object> result =new HashMap<>();
        //查询数据
        boolean delete=consumerService.removeById(id);
        result.put("success",delete);
        //返回json数据
        return jsonTool.writeValueAsString(result);
    }

    //注册的请求处理方法-http
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Consumer consumer) throws JsonProcessingException {
        //hashmap
        HashMap result = new HashMap();
        //判断数据的合法、有效性
        if (consumer.getUsername() == null || consumer.getUsername().equals("")) {
            result.put("code", 0);//表示失败
            result.put("msg", "用户账号名格式，不能为空！！");
            return jsonTool.writeValueAsString(result);
        }
        //时间获取
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        consumer.setUpdateTime(time);
        consumer.setCreateTime(time);
        //调用业务层来查询是否有用户数据
        boolean save = consumerService.save(consumer);
        //返回结果[判断]
        if (save) {//成功操作
            result.put("code", 1);//表示成功
            result.put("msg", "注册成功");
            result.put("userMsg", consumer);
        } else {
            result.put("code", 0);//表示失败
            result.put("msg", "注册错误");
        }
        //返回json格式
        return jsonTool.writeValueAsString(result);
    }

    @RequestMapping("update") //@RequestBody表示解析json格式的数据
    public String update(@RequestBody Consumer consumer) throws JsonProcessingException {
        //定义一个集合，用于存放健值对结果
        HashMap<String, Object> result =new HashMap<>();
        //新增或修改数据
        boolean update=consumerService.saveOrUpdate(consumer);
        result.put("success",update);
        //返回json数据
        return jsonTool.writeValueAsString(result);
    }
}
