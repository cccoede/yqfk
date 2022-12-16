package com.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.entity.Usertoken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsertokenMapper extends BaseMapper<Usertoken> {

    public Usertoken selectByUserid(String userid);

    public Usertoken selectByToken(String token);
}
