package com.web.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
疫苗接种统计
 */

@Data
@TableName("t_yimiaotj")
public class Yimiaotj {

    @TableId(type = IdType.ASSIGN_ID)
    //数据库主键
    private String id;
    //易感人数
    private Integer renshu;
    //已接种一针人数
    private Integer yi;
    //未接种人数
    private Integer wei;
    //已接种二针人数
    private Integer er;
    //已接种三针或以上人数
    private Integer san;
    //未接种率
    private String weilv;
    //一针接种率
    private String yilv;
    //二针接种率
    private String erlv;
    //三针或以上接种率
    private String sanlv;



}
