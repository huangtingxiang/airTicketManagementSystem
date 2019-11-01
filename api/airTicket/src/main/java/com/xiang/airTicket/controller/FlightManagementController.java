package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.FlightManagement;
import com.xiang.airTicket.entity.Plane;
import com.xiang.airTicket.service.FlightManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("flightManagement")
public class FlightManagementController {

    @Autowired
    FlightManagementService flightManagementService;

    // 保存
    @JsonView(BaseJsonView.class)
    @PostMapping
    public FlightManagement save(FlightManagement flightManagement) {
        return flightManagementService.save(flightManagement);
    }

    // 通过id获取
    @GetMapping("/{id}")
    @JsonView(BaseJsonView.class)
    public FlightManagement getById(@PathVariable Long id) {
        return flightManagementService.getById(id);
    }

    // 更新
    @PutMapping("/{id}")
    @JsonView(BaseJsonView.class)
    public FlightManagement update(@PathVariable Long id, FlightManagement flightManagement) {
        return flightManagementService.update(id, flightManagement);
    }

    @GetMapping("/page")
    @JsonView(BaseJsonView.class)
    public Page<FlightManagement> page(@RequestParam Long startingPlaceId,
                                       @RequestParam Long destinationId,
                                       @RequestParam Date startTime,
                                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return flightManagementService.pageByStartingPlaceAndDestinationStartTime(startingPlaceId, destinationId, startTime, pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        flightManagementService.delete(id);
    }

    private interface BaseJsonView extends
            FlightManagement.DestinationJsonView,
            FlightManagement.StartingAirPortJsonView,
            FlightManagement.DestinationAirPortJsonView,
            FlightManagement.PlaneJsonView,
            Plane.AirlineCompanyJsonView,
            FlightManagement.TicketPricesJsonView,
            FlightManagement.TicketOrdersJsonView {
    }

}
