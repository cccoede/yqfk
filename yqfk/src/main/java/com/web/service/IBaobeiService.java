package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.entity.Baobei;
import com.web.utils.PageUtils;

import java.util.Map;


public interface IBaobeiService extends IService<Baobei> {

    //列表信息，支持分页多条件查询
    PageUtils getPage(Map<String, Object> condition);
}
