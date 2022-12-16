package com.web.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
核酸检测统计
 */

@Data
@TableName("t_hesuantj")
public class Hesuantj {

    @TableId(type = IdType.ASSIGN_ID)
    //数据库主键
    private String id;
    //易感人数
    private Integer renshu;
    //核酸检测一天内
    private Integer yi;
    //核酸检测一天到三天
    private Integer san;
    //核酸检测三天到五天
    private Integer wu;
    //核酸检测五天到七天
    private Integer qi;
    //核酸检测大于七天
    private Integer ba;

    //核酸检测一天内占比
    private String yilv;
    //核酸检测一天到三天占比
    private String sanlv;
    //核酸检测三天到五天占比
    private String wulv;
    //核酸检测五天到七天占比
    private String qilv;
    //核酸检测大于七天占比
    private String balv;



}
