package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.enumeration.Role;
import com.xiang.airTicket.exception.NotAuthenticationException;
import com.xiang.airTicket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession httpSession;

    @Override
    public void login(String userName, String password, Role role) {
        User user = userRepository.findAllByUserNameAndRole(userName, role);
        if (user != null && user.getPassWord().equals(password)) {
            httpSession.setAttribute("userId", user.getId());
        } else {
            NotAuthenticationException exception = new NotAuthenticationException("用户名或密码错误");
            throw exception;
        }
    }

    @Override
    public User getCurrentUser() {
        Long id = (Long) httpSession.getAttribute("userId");
        if (id == null) {
            throw new NotAuthenticationException("请先登陆");
        }
        return userRepository.findById(id).get();
    }
}
