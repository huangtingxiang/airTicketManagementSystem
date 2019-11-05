package com.xiang.airTicket.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @JsonView(BaseJsonView.class)
    public User login(@RequestBody User user) {
        return userService.login(user.getUserName(), user.getPassWord());
    }

    @PutMapping("/logout")
    public void logout() {
        this.userService.logout();
    }

    private interface BaseJsonView extends User.VisitorJsonView {
    }

}
