package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.entity.Visitor;
import com.xiang.airTicket.enumeration.Role;
import com.xiang.airTicket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Visitor getCurrentLoginVisitor(HttpServletRequest request) {
        // 解析token获取用户 返回用户旅客
        String token = request.getHeader(UserServiceImpl.tokenHeader);
        Long id = Long.valueOf(CommonService.parseJWT(token).getId());

        User user = userRepository.findById(id).get();
        if (user.getRole() == Role.VISITOR) {
            return user.getVisitor();
        }
        return null;
    }
}
