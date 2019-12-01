package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.Visitor;
import org.springframework.web.multipart.MultipartFile;

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

    void changeName(Long id, String name);

    void changeIdCard(Long id, String idCard);

    void changePhoneNumber(Long id, String phoneNumber);

    String changeImage(MultipartFile file, Visitor visitor);

}
