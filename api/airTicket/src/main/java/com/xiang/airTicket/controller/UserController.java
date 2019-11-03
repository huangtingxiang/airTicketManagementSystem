package com.xiang.airTicket.controller;


import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public void login(@RequestBody User user) {
        userService.login(user.getUserName(), user.getPassWord(), user.getRole());
    }

}
