package com.web.config;


import com.web.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/uploads/**");

    }

    //将yml配置文件的路径赋值给fileUploadPath
    @Value("${files.upload.path}")
    private String fileUploadPath;

    //配置静态资源路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///"+fileUploadPath);
    }


}
