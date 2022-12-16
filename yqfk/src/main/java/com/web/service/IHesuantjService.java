package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.entity.Hesuantj;
import com.web.utils.PageUtils;

import java.util.Map;


public interface IHesuantjService extends IService<Hesuantj> {

    //列表信息，支持分页多条件查询
    PageUtils getPage(Map<String, Object> condition);
}
