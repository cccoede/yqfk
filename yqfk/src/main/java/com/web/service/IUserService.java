package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.entity.User;
import com.web.utils.PageUtils;
import java.util.Map;


public interface IUserService extends IService<User> {

    //用户登录
    public User userlogin(Map<String, Object> condition);

    //列表信息，支持分页多条件查询
    PageUtils getPage(Map<String, Object> condition);

    //判断用户名是否存在
    public User getUserByUsername(String username);

}
