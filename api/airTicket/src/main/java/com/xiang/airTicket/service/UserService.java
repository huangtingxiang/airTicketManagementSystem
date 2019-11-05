package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    // 登陆
    User login(String userName, String password);

    // 获取当前登陆用户
    User getCurrentUser();

    // 注销
    void logout();

    // 增加管理员
    User saveRoot(User user);

    // 假删除用户
    void delete(Long id);

    // 修改密码
    void resetPassWord(Long id, String passWord);

    // 获取所有管理员的分页
    Page<User> getAllRoot(Pageable pageable);

    // 通过名字获取所有用户分页
    Page<User> getAllByUserName(String userName, Pageable pageable);

    void reboot(Long id);

    User getById(Long id);

}
