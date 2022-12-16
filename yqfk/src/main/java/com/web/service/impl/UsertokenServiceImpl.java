package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.entity.User;
import com.web.entity.Usertoken;
import com.web.mapper.UserMapper;
import com.web.mapper.UsertokenMapper;
import com.web.service.IUsertokenService;
import com.web.utils.DesUtil;
import com.web.utils.Result;
import com.web.utils.Util;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;


@Service("usertokenService")
public class UsertokenServiceImpl extends ServiceImpl<UsertokenMapper, Usertoken> implements IUsertokenService {

    @Resource
    private UsertokenMapper usertokenMapper;

    @Resource
    private UserMapper userMapper;


    //1小时后过期
    private final static int EXPIRE = 3600 * 1;

    @Override
    public Result createToken(String userId) {

        User user = userMapper.selectById(userId);

        //生成一个token
        String token = UUID.randomUUID().toString().replace("-","");

        token = user.getRole()+token+"@"+ DesUtil.getEncryptString(userId);

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        Usertoken tokenEntity = usertokenMapper.selectByUserid(userId);
        if(tokenEntity == null){
            tokenEntity = new Usertoken();
            tokenEntity.setUserid(userId);
            tokenEntity.setToken(token);
            tokenEntity.setCuTime(Util.parseTime(now));
            tokenEntity.setExpireTime(Util.parseTime(expireTime));
            tokenEntity.setTokenStatus(1);
            //保存token
            this.save(tokenEntity);
        }else{
            tokenEntity.setToken(token);
            tokenEntity.setCuTime(Util.parseTime(now));
            tokenEntity.setExpireTime(Util.parseTime(expireTime));
            tokenEntity.setTokenStatus(1);
            //更新token
            this.updateById(tokenEntity);
        }

        Result result = Result.ok().put("token", token).put("expire", EXPIRE);

        return result;
    }

    @Override
    public Usertoken queryByToken(String token){

        return usertokenMapper.selectByToken(token);

    }



}
