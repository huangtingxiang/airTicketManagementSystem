package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.Visitor;
import com.xiang.airTicket.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("visitor")
public class VisitorController {

    @Autowired
    VisitorService visitorService;

    /**
     * 获取当前登陆旅客
     *
     * @param request
     * @return
     */
    @GetMapping("/currentVisitor")
    @JsonView(BaseVisitorJsonView.class)
    public Visitor getCurrentVisitor(HttpServletRequest request) {
        return visitorService.getCurrentLoginVisitor(request);
    }


    @PutMapping("/recharge")
    public void recharge(HttpServletRequest request, @RequestBody Double price) {
        visitorService.recharge(price, request);
    }

    @PutMapping("/changeName/{id}")
    public void changeName(@PathVariable Long id, @RequestBody String name) {
        visitorService.changeName(id, name);
    }

    @PutMapping("/changeIdCard/{id}")
    public void changeIdCard(@PathVariable Long id, @RequestBody String idCard) {
        visitorService.changeIdCard(id, idCard);
    }

    @PutMapping("/changePhone/{id}")
    public void changePhone(@PathVariable Long id, @RequestBody String phoneNumber) {
        visitorService.changePhoneNumber(id, phoneNumber);
    }

    private interface BaseVisitorJsonView extends Visitor.UserJsonView {
    }

}
