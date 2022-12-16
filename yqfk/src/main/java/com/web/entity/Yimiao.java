package com.web.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
疫苗接种记录表
 */

@Data
@TableName("t_yimiao")
public class Yimiao {

    @TableId(type = IdType.ASSIGN_ID)
    //数据库主键
    private String id;
    //身份证
    private String sfz;
    //姓名
    private String xm;
    //疫苗名称
    private String ymc;
    //疫苗厂家
    private String ymgs;
    //接种地点
    private String jdzd;
    //接种时间
    private String ctime;
    //备注
    private String beizhu;
}
