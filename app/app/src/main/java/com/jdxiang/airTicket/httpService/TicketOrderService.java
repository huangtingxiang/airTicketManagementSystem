package com.jdxiang.airTicket.httpService;

import com.jdxiang.airTicket.entity.TicketPrice;

/**
 * 订单服务
 */
public class TicketOrderService {

    public static TicketOrderService ticketOrderService;

    public static TicketOrderService getInstance() {
        if (ticketOrderService == null) {
            ticketOrderService = new TicketOrderService();
        }
        return ticketOrderService;
    }

    BaseHttpService httpService = BaseHttpService.getInstance();

    /**
     * 订阅订单
     * @param flightId
     * @param ticketPriceId
     * @param callBack
     */
    public void subscribeOrder(Long flightId, Long ticketPriceId, BaseHttpService.CallBack callBack) {
        httpService.post("ticketOrder/subscribeOrder/" + flightId + "/" + ticketPriceId, new Object(),
                callBack, TicketPrice.class);
    }
}
