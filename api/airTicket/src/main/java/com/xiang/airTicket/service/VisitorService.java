package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.Visitor;

import javax.servlet.http.HttpServletRequest;

public interface VisitorService {

    Visitor getCurrentLoginVisitor(HttpServletRequest request);

}
