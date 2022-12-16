package com.web.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
报备表
 */

@Data
@TableName("t_baobei")
public class Baobei {

    @TableId(type = IdType.ASSIGN_ID)
    //数据库主键
    private String id;
    //报备用户id
    private String userid;
    //用户姓名
    private String name;
    //报备用户身份证
    private String sfz;
    //报备时间
    private String ctime;
    //报备内容
    private String bcontent;



}
