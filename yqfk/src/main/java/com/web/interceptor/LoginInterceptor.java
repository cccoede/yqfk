package com.web.interceptor;

import com.web.utils.Util;
import com.web.entity.Usertoken;
import com.web.service.IUsertokenService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 登录检查
 * 1.配置好拦截器要拦截哪些请求
 * 2.把这些配置放在容器中
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    IUsertokenService usertokenService;

    /**
     * 目标方法执行之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //获取token
        String token = request.getHeader("token");

        //System.out.println("token="+token);
        //如果token值为空提示重新登录
        if(token == null){
            response.setHeader("system_error","token_invalid");
            return false;
        }

        if(usertokenService == null){
            BeanFactory factory = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(request.getServletContext());
            usertokenService = (IUsertokenService) factory
                    .getBean("usertokenService");
        }
        //把该token值放数据库去查，如果为空提示重新登录
        Usertoken userToken = usertokenService.queryByToken(token);

        if(userToken == null){
            response.setHeader("system_error","token_invalid");
            return false;
        }
        //把该token值放数据库去查，如果不为1则是登录失效状态，重新登录
        if(userToken.getTokenStatus() != 1){
            response.setHeader("system_error","token_invalid");
            return false;
        }
        //过期时间
        Date expireTime = Util.getTime(userToken.getExpireTime());
        //当前时间
        Date nowTime = new Date();
        //如果当前时间大于过期时间，则提醒重新登录
        if(nowTime.getTime()>=expireTime.getTime()){
            response.setHeader("system_error","token_invalid");
            return false;
        }

        //重新更新过期时间1小时后过期
        int EXPIRE = 3600 * 1;

        //过期时间
        Date expireTime2 = new Date(nowTime.getTime() + EXPIRE * 1000);
        //System.out.println("token更新时间="+Util.parseTime(expireTime2));
        userToken.setExpireTime(Util.parseTime(expireTime2));

        usertokenService.updateById(userToken);

       return true;


    }


}
