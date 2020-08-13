package com.rightdarkdoc.config;

import com.rightdarkdoc.interceptors.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                //验证出了user请求外的其他请求
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("register")
              //  .excludePathPatterns("/test")
                .excludePathPatterns("/team/{teamid}/view")
                .excludePathPatterns("/team/search")
                .excludePathPatterns("/team/{teamid}/documents")
                .excludePathPatterns("/document/**");
    }

}