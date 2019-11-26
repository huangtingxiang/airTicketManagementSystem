package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.*;
import com.xiang.airTicket.enumeration.OrderStatus;
import com.xiang.airTicket.exception.NotPayForAbility;
import com.xiang.airTicket.repository.TicketOrderRepository;
import com.xiang.airTicket.repository.TransactionRecordRepository;
import com.xiang.airTicket.repository.VisitorRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiang.airTicket.schedule.TicketQueue;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

@Service
public class TicketOrderServiceImpl implements TicketOrderService {

    public static Logger logger = LoggerFactory.getLogger(TicketOrderServiceImpl.class);

    @Autowired
    VisitorService visitorService;

    @Autowired
    FlightManagementService flightManagementService;

    @Autowired
    TicketOrderRepository ticketOrderRepository;

    @Autowired
    TicketQueue ticketQueue;

    @Autowired
    TransactionRecordRepository transactionRecordRepository;

    @Autowired
    VisitorRepository visitorRepository;

    @Override
    public TicketOrder subscribeOrder(Long flightManagementId, Long tickerPriceId, HttpServletRequest httpServletRequest) {
        // 创建订单 设置创建日期
        TicketOrder ticketOrder = new TicketOrder();
        // 获取当前登陆游客
        Visitor visitor = visitorService.getCurrentLoginVisitor(httpServletRequest);
        // 获取所预定航班
        FlightManagement flightManagement = flightManagementService.getById(flightManagementId);
        for (TicketPrice ticketPrice : flightManagement.getTicketPrices()) {
            if (ticketPrice.getId().equals(tickerPriceId)) {
                ticketOrder.setTicketPrice(ticketPrice);
                break;
            }
        }
        ticketOrder.setVisitor(visitor);
        ticketOrder.setFlightManagement(flightManagement);
        ticketOrder.setCreateTime(Calendar.getInstance().getTime());
        // 添加到订单队列
        ticketQueue.add(ticketOrder);
        return ticketOrderRepository.save(ticketOrder);
    }

    @Override
    public void payForOrder(Long id, Visitor visitor) {
        TicketOrder ticketOrder = ticketOrderRepository.findById(id).get();
        logger.info("获取当前用户 查看余额");
        visitor.setBalance(visitor.getBalance() -  ticketOrder.getTicketPrice().getPrice());
        if (visitor.getBalance() < 0) {
            throw new NotPayForAbility("用户余额不足");
        } else {
            visitorRepository.save(visitor);
        }
        logger.info("从取消订单队列移除订单");
        TicketOrder findOrder = ticketQueue.findById(id);
        if (findOrder != null) {
            findOrder.setOrderStatus(OrderStatus.FINISH);
        }
        logger.info("修改订单状态");
        ticketOrder.setOrderStatus(OrderStatus.FINISH);
        ticketOrderRepository.save(ticketOrder);
        logger.info("生成消费记录");
        // 添加交易记录
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setVisitor(visitor);
        transactionRecord.setCreateTime(Calendar.getInstance().getTime());
        transactionRecord.setRecordMessage("支付订单!");
        transactionRecord.setPrice(ticketOrder.getTicketPrice().getPrice());
        transactionRecord.setPayFor(false);
        transactionRecordRepository.save(transactionRecord);
    }

    @Override
    public List<TicketOrder> getAll(Visitor visitor) {
        return ticketOrderRepository.findAllByVisitorOrderByIdDesc(visitor);
    }
}
