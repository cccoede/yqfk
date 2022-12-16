package com.web.controller;

import com.web.entity.User;
import com.web.entity.Usertoken;
import com.web.service.IUserService;
import com.web.service.IUsertokenService;
import com.web.utils.PageUtils;
import com.web.utils.Permission;
import com.web.utils.Result;
import com.web.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ManageController {

    @Autowired
    IUserService userService;

    @Autowired
    IUsertokenService usertokenService;

    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,Object> condition){

        String password = (String)condition.get("password");

        password = Util.getmd5(password, "utf-8");

        condition.put("password",password);

        User bean = userService.userlogin(condition);

        if(bean==null){
            return Result.error("用户名或者密码错误");
        }

        if("禁用".equals(bean.getStatus())){
            return Result.error("该账户已被禁用，登录失败");
        }

        Result result = usertokenService.createToken(bean.getId()+"");
        result.put("code",200);
        result.put("msg","登录成功");

        if(bean.getRole()==1){
            result.put("data",Permission.getManagePermission());
        }else if(bean.getRole()==2){
            result.put("data",Permission.getUserPermission());
        }else if(bean.getRole()==3){
            result.put("data",Permission.getVipPermission());
        }
        return result;
    }


    //修改密码
    @PostMapping("/password")
    public Result password(HttpServletRequest request, @RequestBody Map<String,Object> condition){

        String oldpassword = (String)condition.get("oldpassword");

        String newpassword = (String)condition.get("newpassword");

        oldpassword = Util.getmd5(oldpassword, "utf-8");

        newpassword = Util.getmd5(newpassword, "utf-8");

        condition.put("password",oldpassword);

        //获取token
        String token = request.getHeader("token");

        String userid = Util.parseToken(token);

        //用token值查用户信息，获取用户名
        User user = userService.getById(userid);

        condition.put("username",user.getUsername());

        User bean = userService.userlogin(condition);

        if(bean==null){
            return Result.error("原密码错误，修改失败");
        }
        bean.setPassword(newpassword);

        userService.updateById(bean);

        return Result.ok("修改成功");
    }

    //获取用户信息
    @PostMapping("/getUserinfo")
    public Result getUserinfo(HttpServletRequest request) {
        //获取token
        String token = request.getHeader("token");

        String userid = Util.parseToken(token);

        User bean = userService.getById(userid);

        return Result.okAndData(bean);
    }

    //修改用户信息
    @PostMapping("/userinfoUpdate")
    public Result userinfoUpdate(@RequestBody User entity) {
        userService.updateById(entity);
        return Result.ok("修改成功");
    }

    //用户退出
    @PostMapping("/loginout")
    public Result loginout(HttpServletRequest request){

        //获取token
        String token = request.getHeader("token");

        //把该token值放数据库去查，
        Usertoken userToken = usertokenService.queryByToken(token);
        //设置token为失效状态
        userToken.setTokenStatus(0);
        //更新token表
        usertokenService.updateById(userToken);

        User user = userService.getById(userToken.getUserid());

        user.setLasttime(Util.parseTime(new Date()));

        userService.updateById(user);

        return Result.ok("退出成功");
    }


    //用户信息列表
    @PostMapping("/searchList")
    public Result searchList(@RequestBody Map<String, Object> condition) {

        PageUtils pageResult = userService.getPage(condition);

        return Result.ok().put("pageResult",pageResult);

    }


    //重置用户密码
    @GetMapping("/passwordreset")
    public Result passwordreset(@RequestParam("id") String id) {
        User user = userService.getById(id);

        user.setPassword(Util.getmd5("111111", "utf-8"));

        userService.updateById(user);

        return Result.ok("重置成功");
    }


    //用户注册
    @PostMapping("/register")
    public Result register(@RequestBody User entity) {
        User bean = userService.getUserByUsername(entity.getUsername());

        if(bean!=null){
            return Result.error("该用户名(身份证)已经存在，不能注册");
        }

        entity.setPic("uploads/tx.png");
        entity.setCtime(Util.parseTime(new Date()));
        entity.setRole(3);
        entity.setPassword(Util.getmd5(entity.getPassword(), "utf-8"));
        entity.setStatus("正常");
        userService.save(entity);

        return Result.ok("注册成功");

    }


    //启用/禁用账户
    @GetMapping("/statusreset")
    public Result statusreset(@RequestParam("id") String id,@RequestParam("status") String status) {
        User user = userService.getById(id);
        user.setStatus(status);
        userService.updateById(user);
        return Result.ok("操作成功");
    }
}
