package com.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    public User userlogin(@Param("map") Map<String, Object> map);

    public User selectUserByUsername(@Param("username") String username);


}
