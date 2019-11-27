package com.jdxiang.airTicket.entity;

public class User {
    private Long id;

    private String userName;

    private String passWord;

    private Integer role;

    private boolean status = true;

    private Visitor visitor; // 旅客信息

    public User(String username, String password, Visitor visitor) {
        this.userName = username;
        this.passWord = password;
        this.visitor = visitor;
    }

    public User() {}

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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
}
