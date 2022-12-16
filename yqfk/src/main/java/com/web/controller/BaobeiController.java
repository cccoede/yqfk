package com.web.controller;


import com.web.entity.Baobei;
import com.web.entity.User;
import com.web.service.IBaobeiService;
import com.web.service.IUserService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import com.web.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

//报备信息Controller层
@RestController
@RequestMapping("/baobei")
public class BaobeiController {

    @Autowired
    IBaobeiService baobeiService;
    @Autowired
    IUserService userService;

    //报备信息信息列表
    @PostMapping("/searchList")
    public Result searchList(HttpServletRequest request, @RequestBody Map<String, Object> condition) {

        String token = request.getHeader("token");
        if(token.startsWith("3")){
            String userid = Util.parseToken(token);
            User user = userService.getById(userid);
            condition.put("sfz",user.getUsername());
        }
        PageUtils pageResult = baobeiService.getPage(condition);

        return Result.ok().put("pageResult",pageResult);

    }

    //添加报备信息
    @PostMapping("/add")
    public Result add(HttpServletRequest request,@RequestBody Baobei entity) {

        String token = request.getHeader("token");
        String userid = Util.parseToken(token);
        User user = userService.getById(userid);

        entity.setName(user.getName());
        entity.setSfz(user.getUsername());
        entity.setUserid(user.getId());

        entity.setCtime(Util.parseTime(new Date()));
        baobeiService.save(entity);
        return Result.ok("添加成功");
    }

    //通过id查报备信息信息
    @GetMapping("/getById")
    public Result getById(@RequestParam("id") String id) {
        return Result.okAndData(baobeiService.getById(id));
    }

    //修改信息
    @PostMapping("/update")
    public Result update(@RequestBody Baobei entity) {
        baobeiService.updateById(entity);
        return Result.ok("修改成功");
    }

    //删除信息
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") String id) {
        baobeiService.removeById(id);
        return Result.ok("删除成功");
    }


}
