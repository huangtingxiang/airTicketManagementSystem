package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.Visitor;
import com.xiang.airTicket.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("visitor")
public class VisitorController {

    @Autowired
    VisitorService visitorService;

    @GetMapping
    @JsonView(BaseVisitorJsonView.class)
    public Visitor getCurrentVisitor(HttpServletRequest request) {
        return visitorService.getCurrentLoginVisitor(request);
    }


    private interface BaseVisitorJsonView {
    }

}
