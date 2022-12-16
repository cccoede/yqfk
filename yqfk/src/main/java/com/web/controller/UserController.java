package com.web.controller;


import com.web.entity.User;
import com.web.service.IHesuanService;
import com.web.service.IUserService;
import com.web.service.IYimiaoService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import com.web.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//用户Controller层
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    IYimiaoService yimiaoService;
    @Autowired
    IHesuanService hesuanService;


    //用户信息列表
    @PostMapping("/searchList")
    public Result searchList(HttpServletRequest request, @RequestBody Map<String, Object> condition) {
        PageUtils pageResult = userService.getPage(condition);

        return Result.ok().put("pageResult",pageResult);

    }

    //添加用户
    @PostMapping("/add")
    public Result add(@RequestBody User entity) {
        User bean = userService.getUserByUsername(entity.getUsername());

        if(bean!=null){
            return Result.error("该用户名已经存在，不能注册");
        }
        entity.setPic("uploads/tx.png");
        entity.setCtime(Util.parseTime(new Date()));
        entity.setPassword(Util.getmd5("111111", "utf-8"));
        entity.setStatus("正常");

        userService.save(entity);
        return Result.ok("添加成功，默认密码111111");
    }

    //通过id查用户信息
    @GetMapping("/getById")
    public Result getById(@RequestParam("id") String id) {
        return Result.okAndData(userService.getById(id));
    }

    //修改信息
    @PostMapping("/update")
    public Result update(@RequestBody User entity) {
        userService.updateById(entity);
        return Result.ok("修改成功");
    }

    //删除信息
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") String id) {

        User user = userService.getById(id);

        Map<String, Object> condition = new HashMap<>();
        condition.put("sfz",user.getUsername());
        int count = yimiaoService.getPage(condition).getTotalCount();
        if(count>0){
            return Result.error("操作失败，该用户有疫苗接种信息，不能删除");
        }

        count = hesuanService.getPage(condition).getTotalCount();

        if(count>0){
            return Result.error("操作失败，该用户有核酸检测信息，不能删除");
        }

        userService.removeById(id);
        return Result.ok("删除成功");
    }



    
}
