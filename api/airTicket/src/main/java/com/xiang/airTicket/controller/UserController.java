package com.xiang.airTicket.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    // 登陆
    @PostMapping("/login")
    @JsonView(BaseJsonView.class)
    public User login(@RequestBody User user) {
        return userService.login(user.getUserName(), user.getPassWord());
    }

    // 注销
    @PutMapping("/logout")
    public void logout() {
        this.userService.logout();
    }

    //  添加管理员
    @PostMapping("/root")
    @JsonView(BaseJsonView.class)
    public User saveRoot(@RequestBody User user) {
        return userService.saveRoot(user);
    }

    // 修改密码
    @PutMapping("/resetPassword/{id}")
    public void resetPassword(@PathVariable Long id, @RequestBody User user) {
        userService.resetPassWord(id, user.getPassWord());
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    //  重启用户
    @PutMapping("/reboot/{id}")
    public void reboot(@PathVariable Long id) {
        userService.reboot(id);
    }

    // 获取所有管理员
    @GetMapping("/root")
    @JsonView(BaseJsonView.class)
    public Page<User> getAllRoot(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return userService.getAllRoot(pageable);
    }

    @GetMapping("/getAllByUserName")
    @JsonView(BaseJsonView.class)
    public Page<User> getAllByUserName(@RequestParam(required = false, name = "username") String username,
                                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return this.userService.getAllByUserName(username, pageable);
    }

    @GetMapping("/{id}")
    @JsonView(BaseJsonView.class)
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }


    @GetMapping("/currentUser")
    @JsonView(BaseJsonView.class)
    public User getCurrentUser() {
        return this.userService.getCurrentUser();
    }

    private interface BaseJsonView extends User.VisitorJsonView {
    }

}
