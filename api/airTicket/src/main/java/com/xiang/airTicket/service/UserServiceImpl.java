package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.enumeration.Role;
import com.xiang.airTicket.exception.NotAuthenticationException;
import com.xiang.airTicket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession httpSession;

    @Override
    public User login(String userName, String password) {
        User user = userRepository.findAllByUserName(userName);
        if (user == null || !user.getPassWord().equals(password)) {
            NotAuthenticationException exception = new NotAuthenticationException("用户名或密码错误");
            throw exception;
        } else if (!user.isStatus()){
            NotAuthenticationException exception = new NotAuthenticationException("用户已冻结");
            throw exception;
        } else {
            httpSession.setAttribute("userId", user.getId());
        }
        return user;
    }

    @Override
    public User getCurrentUser() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            throw new NotAuthenticationException("请先登陆");
        }
        return this.userRepository.findById(userId).orElseGet(() -> null);
    }

    @Override
    public void logout() {
        httpSession.setAttribute("userId", null);
    }

    @Override
    public User saveRoot(User user) {
        user.setRole(Role.ROOT);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).get();
        user.setStatus(false);
        userRepository.save(user);
    }

    @Override
    public void resetPassWord(Long id, String passWord) {
        User user = userRepository.findById(id).get();
        user.setPassWord(passWord);
        userRepository.save(user);
    }

    @Override
    public Page<User> getAllRoot(Pageable pageable) {
        return userRepository.findAllByRole(Role.ROOT, pageable);
    }

    @Override
    public Page<User> getAllByUserName(String userName, Pageable pageable) {
        return userRepository.findAllByUserNameLike("%" + userName + "%", pageable);
    }

    @Override
    public void reboot(Long id) {
        User user = userRepository.findById(id).get();
        user.setStatus(true);
        userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }
}
