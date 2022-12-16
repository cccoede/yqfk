package com.web.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web.entity.Echart;
import com.web.entity.Gonggao;
import com.web.entity.Yimiaotj;
import com.web.entity.User;
import com.web.service.IYimiaoService;
import com.web.service.IYimiaotjService;
import com.web.service.IUserService;
import com.web.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

//疫苗接种统计Controller层
@RestController
@RequestMapping("/yimiaotj")
public class YimiaotjController {

    @Autowired
    IYimiaotjService yimiaotjService;
    @Autowired
    IUserService userService;
    @Autowired
    IYimiaoService yimiaoService;


    //疫苗接种统计
    @PostMapping("/searchList")
    public Result searchList(HttpServletRequest request, @RequestBody Map<String, Object> condition) {

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("role",3);

        List<User> userlist =  userService.list(qw);

        PageUtils pageResult = yimiaotjService.getPage(condition);

        List<Yimiaotj> list = (List<Yimiaotj>)pageResult.getList();
        if(list.size()==0){
            Yimiaotj bean = new Yimiaotj();
            bean.setEr(0);
            bean.setErlv("%");
            bean.setRenshu(0);
            bean.setSan(0);
            bean.setSanlv("%");
            bean.setYi(0);
            bean.setYilv("%");
            bean.setWei(0);
            bean.setWeilv("%");


            for(User user:userlist){
                bean.setRenshu(bean.getRenshu()+1);
                Map<String, Object> condition2 = new HashMap<>();
                condition2.put("sfz",user.getUsername());
                int totalCount = yimiaoService.getPage(condition2).getTotalCount();
                if(totalCount==0){
                    bean.setWei(bean.getWei()+1);
                }

                if(totalCount==1){
                    bean.setYi(bean.getYi()+1);
                }
                if(totalCount==2){
                    bean.setEr(bean.getEr()+1);
                }
                if(totalCount>2){
                    bean.setSan(bean.getSan()+1);
                }
            }
            bean.setWeilv(Arith.mul(Arith.div(bean.getWei(),bean.getRenshu(),2),100)+"%");
            bean.setYilv(Arith.mul(Arith.div(bean.getYi(),bean.getRenshu(),2),100)+"%");
            bean.setErlv(Arith.mul(Arith.div(bean.getEr(),bean.getRenshu(),2),100)+"%");
            bean.setSanlv(Arith.mul(Arith.div(bean.getSan(),bean.getRenshu(),2),100)+"%");

            yimiaotjService.save(bean);
        }else{
            Yimiaotj bean = list.get(0);
            bean.setEr(0);
            bean.setErlv("%");
            bean.setRenshu(0);
            bean.setSan(0);
            bean.setSanlv("%");
            bean.setYi(0);
            bean.setYilv("%");
            bean.setWei(0);
            bean.setWeilv("%");

            for(User user:userlist){
                bean.setRenshu(bean.getRenshu()+1);
                Map<String, Object> condition2 = new HashMap<>();
                condition2.put("sfz",user.getUsername());
                int totalCount = yimiaoService.getPage(condition2).getTotalCount();
                if(totalCount==0){
                    bean.setWei(bean.getWei()+1);
                }

                if(totalCount==1){
                    bean.setYi(bean.getYi()+1);
                }
                if(totalCount==2){
                    bean.setEr(bean.getEr()+1);
                }
                if(totalCount>2){
                    bean.setSan(bean.getSan()+1);
                }
            }
            bean.setWeilv(Arith.mul(Arith.div(bean.getWei(),bean.getRenshu(),2),100)+"%");
            bean.setYilv(Arith.mul(Arith.div(bean.getYi(),bean.getRenshu(),2),100)+"%");
            bean.setErlv(Arith.mul(Arith.div(bean.getEr(),bean.getRenshu(),2),100)+"%");
            bean.setSanlv(Arith.mul(Arith.div(bean.getSan(),bean.getRenshu(),2),100)+"%");

            yimiaotjService.updateById(bean);
        }



        pageResult = yimiaotjService.getPage(condition);
        return Result.ok().put("pageResult",pageResult);

    }

    //疫苗接种统计
    @PostMapping("/tongji")
    public Result tongji() {
        List<Yimiaotj> list =  yimiaotjService.list();
        Yimiaotj bean = list.get(0);

        List<Echart> list2 =  new ArrayList<Echart>();

        Echart e1 = new Echart();
        e1.setName("未接种人数（"+bean.getWeilv()+"）");
        e1.setValue(bean.getWei());
        list2.add(e1);

        Echart e2 = new Echart();
        e2.setName("已接种一针人数（"+bean.getYilv()+"）");
        e2.setValue(bean.getYi());
        list2.add(e2);

        Echart e3 = new Echart();
        e3.setName("已接种二针人数（"+bean.getErlv()+"）");
        e3.setValue(bean.getEr());
        list2.add(e3);

        Echart e4 = new Echart();
        e4.setName("已接种三针或以上人数（"+bean.getSanlv()+"）");
        e4.setValue(bean.getSan());
        list2.add(e4);

        Result result = new Result();
        result.put("data",list2);
        return result;
    }

}
