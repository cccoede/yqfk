package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.entity.Usertoken;
import com.web.utils.Result;


public interface IUsertokenService extends IService<Usertoken> {

    //用户ID通过生成token
    Result createToken(String userid);

    //通过token值查token对象
    Usertoken queryByToken(String token);


}
