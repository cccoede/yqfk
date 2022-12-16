package com.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web.entity.*;
import com.web.service.IUserService;
import com.web.service.IHesuanService;
import com.web.service.IHesuantjService;
import com.web.utils.Arith;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import com.web.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

//核酸检测统计Controller层
@RestController
@RequestMapping("/hesuantj")
public class HesuantjController {

    @Autowired
    IHesuantjService hesuantjService;
    @Autowired
    IUserService userService;
    @Autowired
    IHesuanService hesuanService;


    //核酸检测统计
    @PostMapping("/searchList")
    public Result searchList(HttpServletRequest request, @RequestBody Map<String, Object> condition) throws ParseException {

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("role",3);

        List<User> userlist =  userService.list(qw);

        PageUtils pageResult = hesuantjService.getPage(condition);

        List<Hesuantj> list = (List<Hesuantj>)pageResult.getList();
        if(list.size()==0){
            Hesuantj bean = new Hesuantj();

            bean.setRenshu(0);
            bean.setSan(0);
            bean.setSanlv("%");
            bean.setYi(0);
            bean.setYilv("%");
            bean.setWu(0);
            bean.setWulv("%");
            bean.setQi(0);
            bean.setQilv("%");
            bean.setBa(0);
            bean.setBalv("%");

            for(User user:userlist){
                bean.setRenshu(bean.getRenshu()+1);
                QueryWrapper<Hesuan> qw2 = new QueryWrapper<>();
                qw2.eq("sfz",user.getUsername());
                qw2.orderByDesc("ctime");

                List<Hesuan> hslist =  hesuanService.list(qw2);

                if(hslist.size()<=0){
                    bean.setBa(bean.getBa()+1);
                }else{
                    String ctime = hslist.get(0).getCtime();

                    int days = Util.daysBetween(Util.getTime(ctime),new Date());

                    if(days<=1){
                        bean.setYi(bean.getYi()+1);
                    }else if(days<=3){
                        bean.setSan(bean.getSan()+1);
                    }else if(days<=5){
                        bean.setWu(bean.getWu()+1);
                    }else if(days<=7){
                        bean.setQi(bean.getQi()+1);
                    }
                }
            }

            bean.setYilv(Arith.mul(Arith.div(bean.getYi(),bean.getRenshu(),2),100)+"%");
            bean.setSanlv(Arith.mul(Arith.div(bean.getSan(),bean.getRenshu(),2),100)+"%");
            bean.setWulv(Arith.mul(Arith.div(bean.getWu(),bean.getRenshu(),2),100)+"%");
            bean.setQilv(Arith.mul(Arith.div(bean.getQi(),bean.getRenshu(),2),100)+"%");
            bean.setBalv(Arith.mul(Arith.div(bean.getBa(),bean.getRenshu(),2),100)+"%");

            hesuantjService.save(bean);
        }else{
            Hesuantj bean = list.get(0);

            bean.setRenshu(0);
            bean.setSan(0);
            bean.setSanlv("%");
            bean.setYi(0);
            bean.setYilv("%");
            bean.setWu(0);
            bean.setWulv("%");
            bean.setQi(0);
            bean.setQilv("%");
            bean.setBa(0);
            bean.setBalv("%");

            for(User user:userlist){
                bean.setRenshu(bean.getRenshu()+1);
                QueryWrapper<Hesuan> qw2 = new QueryWrapper<>();
                qw2.eq("sfz",user.getUsername());
                qw2.orderByDesc("ctime");

                List<Hesuan> hslist =  hesuanService.list(qw2);

                if(hslist.size()<=0){
                    bean.setBa(bean.getBa()+1);
                }else{
                    String ctime = hslist.get(0).getCtime();

                    int days = Util.daysBetween(Util.getTime(ctime),new Date());

                    if(days<=1){
                        bean.setYi(bean.getYi()+1);
                    }else if(days<=3){
                        bean.setSan(bean.getSan()+1);
                    }else if(days<=5){
                        bean.setWu(bean.getWu()+1);
                    }else if(days<=7){
                        bean.setQi(bean.getQi()+1);
                    }
                }
            }

            bean.setYilv(Arith.mul(Arith.div(bean.getYi(),bean.getRenshu(),2),100)+"%");
            bean.setSanlv(Arith.mul(Arith.div(bean.getSan(),bean.getRenshu(),2),100)+"%");
            bean.setWulv(Arith.mul(Arith.div(bean.getWu(),bean.getRenshu(),2),100)+"%");
            bean.setQilv(Arith.mul(Arith.div(bean.getQi(),bean.getRenshu(),2),100)+"%");
            bean.setBalv(Arith.mul(Arith.div(bean.getBa(),bean.getRenshu(),2),100)+"%");

            hesuantjService.updateById(bean);
        }



        pageResult = hesuantjService.getPage(condition);

        return Result.ok().put("pageResult",pageResult);

    }

    //核酸检测统计
    @PostMapping("/tongji")
    public Result tongji() {
        List<Hesuantj> list =  hesuantjService.list();
        Hesuantj bean = list.get(0);

        List<Echart> list2 =  new ArrayList<Echart>();

        Echart e1 = new Echart();
        e1.setName("核酸检测一天内（"+bean.getYilv()+"）");
        e1.setValue(bean.getYi());
        list2.add(e1);

        Echart e2 = new Echart();
        e2.setName("核酸检测一天到三天（"+bean.getSanlv()+"）");
        e2.setValue(bean.getSan());
        list2.add(e2);

        Echart e3 = new Echart();
        e3.setName("核酸检测三天到五天（"+bean.getWulv()+"）");
        e3.setValue(bean.getWu());
        list2.add(e3);

        Echart e4 = new Echart();
        e4.setName("核酸检测五天到七天（"+bean.getQilv()+"）");
        e4.setValue(bean.getQi());
        list2.add(e4);

        Echart e5 = new Echart();
        e5.setName("核酸检测大于七天（"+bean.getBalv()+"）");
        e5.setValue(bean.getBa());
        list2.add(e5);

        Result result = new Result();
        result.put("data",list2);
        return result;
    }



}
