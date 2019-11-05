package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.enumeration.Role;

public interface UserService {

    // 登陆
    User login(String userName, String password);

    // 获取当前登陆用户
    User getCurrentUser();

    // 注销
    void logout();
}
