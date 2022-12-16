package com.web.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
用户token表
 */
@Data
@TableName("t_usertoken")
public class Usertoken {

    @TableId(type = IdType.ASSIGN_ID)
    //数据库主键
    private String id;
    //用户id
    private String userid;
    //token值
    private String token;
    //过期时间
    private String expireTime;
    //创建或更新时间
    private String cuTime;
    //token状态,1表示有效，0表示失效
    private Integer tokenStatus;

}
