package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.Visitor;

import javax.servlet.http.HttpServletRequest;

public interface VisitorService {

    /**
     * 获取当前登陆游客
     *
     * @param request
     * @return
     */
    Visitor getCurrentLoginVisitor(HttpServletRequest request);

    /**
     * 当前登陆游客充值
     *
     * @param price
     */
    void recharge(Double price, HttpServletRequest request);

}
