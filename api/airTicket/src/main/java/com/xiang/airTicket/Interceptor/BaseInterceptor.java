package com.xiang.airTicket.Interceptor;

import com.xiang.airTicket.exception.NotAuthenticationException;
import com.xiang.airTicket.service.UserService;
import com.xiang.airTicket.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BaseInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Autowired
    VisitorService visitorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头获取token进行判断
        String token = request.getHeader("Authorization");
        if (token != null) {
            // 通过token获取登陆用户
            if (visitorService.getCurrentLoginVisitor(request) == null) {
                throw new NotAuthenticationException("请先登陆");
            }
        } else {
            // 从session中判断是否登陆
            if (userService.getCurrentUser() == null) {
                throw new NotAuthenticationException("请先登陆");
            }
        }

        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }
}
