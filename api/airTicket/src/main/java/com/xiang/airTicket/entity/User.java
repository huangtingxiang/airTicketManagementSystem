package com.xiang.airTicket.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.enumeration.Role;

import javax.persistence.*;

// 用户表
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String passWord;

    private Role role;

    @OneToOne
    @JsonView(VisitorJsonView.class)
    private Visitor visitor; // 旅客信息

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public interface VisitorJsonView {
    }
}
