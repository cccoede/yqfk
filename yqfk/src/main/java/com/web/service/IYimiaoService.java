package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.entity.Yimiao;
import com.web.utils.PageUtils;

import java.util.Map;


public interface IYimiaoService extends IService<Yimiao> {

    //列表信息，支持分页多条件查询
    PageUtils getPage(Map<String, Object> condition);
}
