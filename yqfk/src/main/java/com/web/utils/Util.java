package com.web.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Util {

    // MD5加密
    public static String getmd5(String plainText,String cset) {
        String str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes(cset));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();// 32位的加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    // 获取系统时间
    public static String parseTime2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date.getTime());
    }

    // 获取系统时间
    public static String parseTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date.getTime());
    }

    // 解析当前时间
    public static Date getTime(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date =sdf.parse(time);
        return date;
    }

    public static String parseToken(String token){
        int end = token.length();
        int start = token.indexOf("@")+1;
        String userid = DesUtil.getDecryptString(token.substring(start,end));
        return userid;
    }

    //计算2个日期差多少天日期格式
    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }




}
