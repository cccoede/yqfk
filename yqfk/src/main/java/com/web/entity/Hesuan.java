package com.web.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
核酸检测记录表
 */

@Data
@TableName("t_hesuan")
public class Hesuan {

    @TableId(type = IdType.ASSIGN_ID)
    //数据库主键
    private String id;
    //身份证
    private String sfz;
    //姓名
    private String xm;
    //采样地点
    private String address;
    //采样时间
    private String ctime;
    //采样机构
    private String jigou;
    //采样结果
    private String jieguo;
    //备注
    private String beizhu;
    //是否复核  未复核/复核阳性/复核阴性
    private String status;
    //复核时间
    private String ftime;

    @TableField(exist = false)
    private String excelpath;
}
