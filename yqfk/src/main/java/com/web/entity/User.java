package com.web.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
/*
用户表
 */

@Data
@TableName("t_user")
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    //数据库主键
    private String id;
    //用户名(身份证号)
    private String username;
    //密码
    private String password;
    //姓名
    private String name;
    //用户角色，1表示系统管理员，2表示工作人员 3表示易感人群
    private Integer role;
    //手机号码
    private String tel;
    //头像
    private String pic;
    //添加时间
    private String ctime;
    //上次登录时间
    private String lasttime;
    //登录状态  正常/ 禁用
    private String status;
    //工作单位
    private String workunit;
    //户籍地
    private String hjd;
    //居住地
    private String jzd;
    //性别
    private String sex;
    //文化程度
    private String whcd;
    //备注
    private String beizhu;

}
