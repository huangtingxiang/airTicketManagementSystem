package com.xiang.airTicket.schedule;

import com.xiang.airTicket.entity.TicketOrder;
import com.xiang.airTicket.enumeration.OrderStatus;
import com.xiang.airTicket.repository.TicketOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;


@Component
public class TicketQueue {

    public static Logger logger = LoggerFactory.getLogger(TicketQueue.class);

    @Autowired
    TicketOrderRepository ticketOrderRepository;

    /**
     * 自定义维持的订单队列
     * 始终保持队列首部为创建时间最小的订单
     */
    PriorityBlockingQueue<TicketOrder> priorityBlockingQueue = new PriorityBlockingQueue<TicketOrder>(100, new Comparator<TicketOrder>() {
        @Override
        public int compare(TicketOrder o1, TicketOrder o2) {
            return o1.getCreateTime().compareTo(o2.getCreateTime());
        }
    });


    public void add(TicketOrder ticketOrder) {
        logger.info("添加新订单");
        this.priorityBlockingQueue.add(ticketOrder);
    }

    public TicketOrder findById(Long id) {
        for (Iterator<TicketOrder> it = this.priorityBlockingQueue.iterator(); it.hasNext(); ) {
            TicketOrder ticketOrder = it.next();
            if (ticketOrder.getId() == id) {
                return ticketOrder;
            }
        }
        return null;
    }

    public void pushTicketOrder() {
        logger.info("循环获取队首订单若订单状态为完成，直接移除订单。若订单时间大于五分钟，退队列");
        while (!this.priorityBlockingQueue.isEmpty()) {
            // 获取队首元素
            TicketOrder ticketOrder = this.priorityBlockingQueue.peek();
            // 当队首订单为预约中并且时间大于五分钟 修改订单状态 并保存至数据库
            // 否则直接将订单退队列
            if (ticketOrder.getOrderStatus() == OrderStatus.SUBSCRIBE) {
                Calendar nowDate = Calendar.getInstance();
                Calendar after5Minute = Calendar.getInstance();
                after5Minute.setTime(ticketOrder.getCreateTime());
                // 订单创建时间五分钟后
                after5Minute.add(Calendar.MINUTE, 5);
                if (nowDate.after(after5Minute)) {
                    ticketOrder.setOrderStatus(OrderStatus.CANCEL);
                    ticketOrderRepository.save(ticketOrder);
                } else {
                    break;
                }
            }
            this.priorityBlockingQueue.poll();
        }
    }

    /**
     * 每10秒检查一次队列
     */
    @Scheduled(cron = "0/10 * *  * * ? ")
    public void checkQueue() {
        pushTicketOrder();
    }
}
