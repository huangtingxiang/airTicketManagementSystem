package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.TicketOrder;
import com.xiang.airTicket.service.TicketOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("ticketOrder")
public class TicketOrderController {

    @Autowired
    TicketOrderService ticketOrderService;

    /**
     * 预定订单
     *
     * @param flightId
     * @param request
     * @return
     */
    @PostMapping("/subscribeOrder/{flightId}/{ticketPriceId}")
    @JsonView(TicketOrderController.BaseJsonView.class)
    public TicketOrder subscribeOrder(@PathVariable Long flightId, @PathVariable Long ticketPriceId, HttpServletRequest request) {
        TicketOrder ticketOrder = ticketOrderService.subscribeOrder(flightId, ticketPriceId, request);
        return ticketOrderService.subscribeOrder(flightId, ticketPriceId, request);
    }

    private interface BaseJsonView extends TicketOrder.SeatJsonView {
    }

}
