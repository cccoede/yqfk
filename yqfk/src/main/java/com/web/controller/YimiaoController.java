package com.web.controller;


import com.web.entity.User;
import com.web.entity.Yimiao;
import com.web.service.IUserService;
import com.web.service.IYimiaoService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import com.web.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

//疫苗接种信息Controller层
@RestController
@RequestMapping("/yimiao")
public class YimiaoController {

    @Autowired
    IYimiaoService yimiaoService;
    @Autowired
    private IUserService userService;

    //疫苗接种信息信息列表
    @PostMapping("/searchList")
    public Result searchList(HttpServletRequest request, @RequestBody Map<String, Object> condition) {

        String token = request.getHeader("token");
        if(token.startsWith("3")){
            String userid = Util.parseToken(token);
            User user = userService.getById(userid);
            condition.put("sfz",user.getUsername());
        }

        PageUtils pageResult = yimiaoService.getPage(condition);

        return Result.ok().put("pageResult",pageResult);

    }

    //添加疫苗接种信息
    @PostMapping("/add")
    public Result add(@RequestBody Yimiao entity) {

        User user = userService.getUserByUsername(entity.getSfz());
        if(user==null){
            user = new User();
            user.setUsername(entity.getSfz());
            user.setName(entity.getXm());
            user.setPic("uploads/tx.png");
            user.setCtime(Util.parseTime(new Date()));
            user.setPassword(Util.getmd5("111111", "utf-8"));
            user.setStatus("正常");
            user.setRole(3);
            userService.save(user);
        }

        yimiaoService.save(entity);
        return Result.ok("添加成功");
    }

    //通过id查疫苗接种信息信息
    @GetMapping("/getById")
    public Result getById(@RequestParam("id") String id) {
        return Result.okAndData(yimiaoService.getById(id));
    }

    //修改信息
    @PostMapping("/update")
    public Result update(@RequestBody Yimiao entity) {
        yimiaoService.updateById(entity);
        return Result.ok("修改成功");
    }

    //删除信息
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") String id) {
        yimiaoService.removeById(id);
        return Result.ok("删除成功");
    }



}
