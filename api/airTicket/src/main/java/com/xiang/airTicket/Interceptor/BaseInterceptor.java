package com.xiang.airTicket.Interceptor;

import com.xiang.airTicket.exception.NotAuthenticationException;
import com.xiang.airTicket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BaseInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (userService.getCurrentUser() == null) {
            throw new NotAuthenticationException("请先登陆");
        }
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }
}
