package com.web.controller;


import com.web.entity.Gonggao;
import com.web.service.IGonggaoService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import com.web.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

//疫情信息Controller层
@RestController
@RequestMapping("/gonggao")
public class GonggaoController {

    @Autowired
    IGonggaoService gonggaoService;

    //疫情信息信息列表
    @PostMapping("/searchList")
    public Result searchList(@RequestBody Map<String, Object> condition) {

        PageUtils pageResult = gonggaoService.getPage(condition);

        return Result.ok().put("pageResult",pageResult);

    }

    //添加疫情信息
    @PostMapping("/add")
    public Result add(@RequestBody Gonggao entity) {
        entity.setCtime(Util.parseTime(new Date()));
        gonggaoService.save(entity);
        return Result.ok("添加成功");
    }

    //通过id查疫情信息信息
    @GetMapping("/getById")
    public Result getById(@RequestParam("id") String id) {
        return Result.okAndData(gonggaoService.getById(id));
    }

    //修改信息
    @PostMapping("/update")
    public Result update(@RequestBody Gonggao entity) {
        gonggaoService.updateById(entity);
        return Result.ok("修改成功");
    }

    //删除信息
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") String id) {
        gonggaoService.removeById(id);
        return Result.ok("删除成功");
    }


}
