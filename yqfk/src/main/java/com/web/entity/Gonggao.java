package com.web.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
疫情信息表
 */

@Data
@TableName("t_gonggao")
public class Gonggao {

    @TableId(type = IdType.ASSIGN_ID)
    //数据库主键
    private String id;
    //疫情信息标题
    private String gtitle;
    //疫情信息内容
    private String gcontent;
    //添加时间
    private String ctime;



}
