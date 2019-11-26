package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.FlightManagement;
import com.xiang.airTicket.entity.Plane;
import com.xiang.airTicket.entity.TicketOrder;
import com.xiang.airTicket.entity.Visitor;
import com.xiang.airTicket.service.TicketOrderService;
import com.xiang.airTicket.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("ticketOrder")
public class TicketOrderController {

    @Autowired
    TicketOrderService ticketOrderService;

    @Autowired
    VisitorService visitorService;


    @GetMapping("/getAll")
    @JsonView(GetJsonView.class)
    public List<TicketOrder> getAll(HttpServletRequest request) {
        Visitor visitor = visitorService.getCurrentLoginVisitor(request);
        List<TicketOrder> ticketOrders = ticketOrderService.getAll(visitor);
        for (TicketOrder ticketOrder :
                ticketOrders) {
            ticketOrder.getFlightManagement().setOrders(null);
        }
        return ticketOrders;
    }

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
        return ticketOrderService.subscribeOrder(flightId, ticketPriceId, request);
    }

    /**
     * 支付订单
     *
     * @param id
     * @param request
     */
    @PutMapping("/payFor/{id}")
    public void payFor(@PathVariable Long id, HttpServletRequest request) {
        ticketOrderService.payForOrder(id, visitorService.getCurrentLoginVisitor(request));
    }

    private interface BaseJsonView extends TicketOrder.SeatJsonView {
    }

    private interface GetJsonView extends BaseJsonView,
            TicketOrder.FlightManagementJsonView,
            TicketOrder.SeatJsonView,
            TicketOrder.TicketPriceJsonView, FlightManagement.DestinationJsonView,
            FlightManagement.StartingAirPortJsonView,
            FlightManagement.DestinationAirPortJsonView,
            FlightManagement.PlaneJsonView,
            Plane.AirlineCompanyJsonView,
            Plane.ShipSpaceJsonView,
            FlightManagement.StartingPlaceJsonView,
            FlightManagement.TicketPricesJsonView {
    }

}
