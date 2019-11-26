package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.TransactionRecord;
import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.entity.Visitor;
import com.xiang.airTicket.enumeration.Role;
import com.xiang.airTicket.repository.TransactionRecordRepository;
import com.xiang.airTicket.repository.UserRepository;
import com.xiang.airTicket.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRecordRepository transactionRecordRepository;

    @Autowired
    VisitorRepository visitorRepository;

    @Override
    public Visitor getCurrentLoginVisitor(HttpServletRequest request) {
        // 解析token获取用户 返回用户旅客
        String token = request.getHeader(UserServiceImpl.tokenHeader);
        Long id = Long.valueOf(CommonService.parseJWT(token).getId());

        User user = userRepository.findById(id).get();
        if (user.getRole() == Role.VISITOR) {
            return user.getVisitor();
        }
        return null;
    }

    @Override
    public void recharge(Double price, HttpServletRequest request) {
        // 获取当前登陆游客
        Visitor visitor = getCurrentLoginVisitor(request);
        visitor.setBalance(visitor.getBalance() + price);
        // 添加交易记录
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setVisitor(visitor);
        transactionRecord.setCreateTime(Calendar.getInstance().getTime());
        transactionRecord.setRecordMessage("充值成功!");
        transactionRecord.setPrice(price);
        transactionRecordRepository.save(transactionRecord);

        visitorRepository.save(visitor);
    }
}
