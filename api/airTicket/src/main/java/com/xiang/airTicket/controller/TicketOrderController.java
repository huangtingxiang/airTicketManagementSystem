package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.*;
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
            ticketOrder.getFlightManagement().setTicketOrders(null);
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
     * 手动取消订单
     *
     * @param id
     */
    @PutMapping("/cancelSubscribe/{id}")
    public void cancelSubscribe(@PathVariable Long id) {
        ticketOrderService.cancelSubscribe(id);
    }

    /**
     * 退票
     *
     * @param id
     */
    @PutMapping("/cancelPayFor/{id}")
    public void cancelPayFor(@PathVariable Long id, HttpServletRequest request) {
        Visitor visitor = visitorService.getCurrentLoginVisitor(request);
        ticketOrderService.cancelPayFor(id, visitor);
    }

    /**
     * 完成订单
     *
     * @param id
     */
    @PutMapping("/finish/{id}")
    public void finishOrder(@PathVariable Long id) {
        ticketOrderService.finishOrder(id);
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

    /**
     * 值机 选择座位
     *
     * @param id
     * @param seat
     */
    @PutMapping("/selectSeat/{id}")
    public void selectSeat(@PathVariable Long id, @RequestBody Seat seat) {
        ticketOrderService.selectSeat(id, seat);
    }

    @GetMapping("/{id}")
    @JsonView(GetJsonView.class)
    public TicketOrder getById(@PathVariable Long id) {
        return ticketOrderService.getById(id);
    }

    /**
     * 获取已经值机的订单
     *
     * @param flightId
     * @return
     */
    @GetMapping("/getFinishByFlightId/{flightId}")
    @JsonView(BaseJsonView.class)
    public List<TicketOrder> getFinishByFlightId(@PathVariable Long flightId) {
        return ticketOrderService.getFinishByFlightId(flightId);
    }

    /**
     * 改签
     *
     * @param id
     * @param ticketOrder
     * @return
     */
    @PutMapping("/changeOrder/{id}")
    @JsonView(GetJsonView.class)
    public TicketOrder changeOrder(@PathVariable Long id, @RequestBody TicketOrder ticketOrder, HttpServletRequest request) {

        return ticketOrderService.changeOrder(id, ticketOrder, visitorService.getCurrentLoginVisitor(request));
    }

    private interface BaseJsonView extends TicketOrder.SeatJsonView {
    }

    private interface GetJsonView extends BaseJsonView,
            TicketOrder.FlightManagementJsonView,
            TicketOrder.SeatJsonView,
            TicketOrder.VisitorJsonView,
            TicketOrder.TicketPriceJsonView,
            FlightManagement.DestinationJsonView,
            FlightManagement.StartingAirPortJsonView,
            FlightManagement.DestinationAirPortJsonView,
            FlightManagement.PlaneJsonView,
            Plane.AirlineCompanyJsonView,
            Plane.ShipSpaceJsonView,
            FlightManagement.StartingPlaceJsonView,
            FlightManagement.TicketPricesJsonView {
    }

}
