package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.TransactionRecord;
import com.xiang.airTicket.entity.Visitor;
import com.xiang.airTicket.repository.TransactionRecordRepository;
import com.xiang.airTicket.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("transactionRecord")
public class TransactionRecordController {

    @Autowired
    TransactionRecordRepository transactionRecordRepository;

    @Autowired
    VisitorService visitorService;


    @GetMapping
    @JsonView(BaseJsonView.class)
    List<TransactionRecord> getAll(HttpServletRequest request) {
        Visitor visitor = visitorService.getCurrentLoginVisitor(request);
        return transactionRecordRepository.findAllByVisitorOrderByIdDesc(visitor);
    }

    private interface BaseJsonView {
    }

}
