package com.xiang.airTicket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiang.airTicket.Interceptor.BaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    BaseInterceptor baseInterceptor;

    // 添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("user/loginByToken");
    }


    // 配置jsonview
    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        final ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().defaultViewInclusion(true).build();
        converters.add(new MappingJackson2HttpMessageConverter(mapper));
    }

}
