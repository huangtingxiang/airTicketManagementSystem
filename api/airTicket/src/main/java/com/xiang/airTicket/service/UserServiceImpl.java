package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.entity.Visitor;
import com.xiang.airTicket.enumeration.Role;
import com.xiang.airTicket.exception.NotAuthenticationException;
import com.xiang.airTicket.repository.UserRepository;
import com.xiang.airTicket.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    public static String tokenHeader = "Authorization";

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession httpSession;

    @Autowired
    VisitorRepository visitorRepository;


    @Override
    public User login(String userName, String password) {
        User user = assertLogin(userName, password);
        httpSession.setAttribute("userId", user.getId());
        return user;
    }

    @Override
    public User loginByToken(String userName, String password, HttpServletResponse response) {
        // 进行用户名密码验证
        User user = assertLogin(userName, password);
        // 判断成功 返回头加入token
        // 生成token
        String token = CommonService.createJwtToken(user.getId());
        response.setHeader("Authorization", token);
        return user;
    }

    private User assertLogin(String username, String password) {
        User user = userRepository.findAllByUserName(username);
        if (user == null || !user.getPassWord().equals(password)) {
            NotAuthenticationException exception = new NotAuthenticationException("用户名或密码错误");
            throw exception;
        } else if (!user.isStatus()) {
            NotAuthenticationException exception = new NotAuthenticationException("用户已冻结");
            throw exception;
        } else {
            return user;
        }
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
    public User getCurrentUserByToken(String token) {
        Long userId = Long.valueOf(CommonService.parseJWT(token).getId());
        if (userId == null) {
            throw new NotAuthenticationException("请先登陆");
        }
        return this.userRepository.findById(userId).orElseGet(() -> null);
    }

    @Override
    public User register(User user, HttpServletResponse response) {
        // 保存旅客
        Visitor visitor = visitorRepository.save(user.getVisitor());
        // 保存用户
        user.setVisitor(visitor);
        user.setRole(Role.VISITOR);
        user = userRepository.save(user);
        // 生成token 返回token
        String token = CommonService.createJwtToken(user.getId());
        response.setHeader(tokenHeader, token);
        return user;
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

    @Override
    public void resetUserName(Long id, String username) {
        User user = userRepository.findById(id).get();
        user.setUserName(username);
        userRepository.save(user);
    }
}
