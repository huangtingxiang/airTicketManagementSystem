package com.jdxiang.airTicket.httpService;

import com.jdxiang.airTicket.entity.TicketOrder;

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
     *
     * @param flightId
     * @param ticketPriceId
     * @param callBack
     */
    public void subscribeOrder(Long flightId, Long ticketPriceId, BaseHttpService.CallBack callBack) {
        httpService.post("ticketOrder/subscribeOrder/" + flightId + "/" + ticketPriceId, new Object(),
                callBack, TicketOrder.class);
    }

    /**
     * 支付订单
     * @param orderId
     * @param callBack
     */
    public void payForOrder(Long orderId, BaseHttpService.CallBack callBack) {
        httpService.put("ticketOrder/payFor/" + orderId, new Object(), callBack, null);
    }

    /**
     * 获取所有订单
     * @param callBack
     */
    public void  getAll(BaseHttpService.CallBack callBack) {
        httpService.get("ticketOrder/getAll", callBack, TicketOrder[].class);

    }
}
