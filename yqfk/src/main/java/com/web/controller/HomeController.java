package com.web.controller;


import com.web.entity.User;
import com.web.service.IUserService;
import com.web.utils.Result;
import com.web.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

//首页Controller层
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    IUserService userService;


    //用户信息
    @PostMapping("/userinfo")
    public Result userinfo(HttpServletRequest request) {

        //获取token
        String token = request.getHeader("token");

        String userid = Util.parseToken(token);

        //用token值查用户信息，获取用户名
        User user = userService.getById(userid);

        return Result.ok().put("data",user);

    }








}
