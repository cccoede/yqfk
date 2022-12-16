package com.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.entity.Yimiaotj;
import com.web.mapper.YimiaotjMapper;
import com.web.service.IYimiaotjService;
import com.web.utils.MapUtils;
import com.web.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service
public class YimiaotjServiceImpl extends ServiceImpl<YimiaotjMapper, Yimiaotj> implements IYimiaotjService {

    @Resource
    YimiaotjMapper yimiaotjMapper;

    //列表信息，支持分页多条件查询
    @Override
    public PageUtils getPage(Map<String, Object> condition) {

        //默认显示第一页
        Integer currPage = MapUtils.isNotBlank(condition, "page") ? (Integer) condition.get("page") : 1;

        //默认每页显示10条
        Integer pageSize = MapUtils.isNotBlank(condition, "limit") ? (Integer) condition.get("limit") : 10;
        //新建分页对象
        Page<Yimiaotj> page = new Page<>(currPage, pageSize);
        //新建查询对象
        QueryWrapper<Yimiaotj> qw = new QueryWrapper<>();
        //查询条件


        //数据排序
        qw.orderByDesc("id");
        //调用mapper层进行查询
        return new PageUtils(baseMapper.selectPage(page, qw));

    }



}
