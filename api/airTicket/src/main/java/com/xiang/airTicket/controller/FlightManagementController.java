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
import java.util.List;

@RestController
@RequestMapping("flightManagement")
public class FlightManagementController {

    @Autowired
    FlightManagementService flightManagementService;

    // 保存
    @JsonView(BaseJsonView.class)
    @PostMapping
    public FlightManagement save(@RequestBody FlightManagement flightManagement) {
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
    public FlightManagement update(@PathVariable Long id, @RequestBody FlightManagement flightManagement) {
        return flightManagementService.update(id, flightManagement);
    }

    @GetMapping("/page")
    @JsonView(BaseJsonView.class)
    public Page<FlightManagement> page(@RequestParam(required = false) Long startingPlaceId,
                                       @RequestParam(required = false) Long destinationId,
                                       @RequestParam(required = false) Long startTime,
                                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Date date = null;
        if (startTime != null) {
            date = new Date(startTime);
        }
        return flightManagementService.pageByStartingPlaceAndDestinationStartTime(startingPlaceId, destinationId, date, pageable);
    }

    @GetMapping("/pageContent")
    @JsonView(BaseJsonView.class)
    public List<FlightManagement> pageContent(@RequestParam(required = false) Long startingPlaceId,
                                              @RequestParam(required = false) Long destinationId,
                                              @RequestParam(required = false) Long startTime,
                                              @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Date date = null;
        if (startTime != null) {
            date = new Date(startTime);
        }
        return flightManagementService.pageByStartingPlaceAndDestinationStartTimeGetContent(startingPlaceId, destinationId, date, pageable).getContent();
    }

    @GetMapping("/searchFlight")
    @JsonView(BaseJsonView.class)
    public List<FlightManagement> searchFlight(@RequestParam(required = false) Long startingPlaceId,
                                               @RequestParam(required = false) Long destinationId,
                                               @RequestParam(required = false) Long startTime) {
        Date date = null;
        if (startTime != null) {
            date = new Date(startTime);
        }
        List data = flightManagementService.searchFlight(startingPlaceId, destinationId, date);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        flightManagementService.delete(id);
    }

    public interface BaseJsonView extends
            FlightManagement.DestinationJsonView,
            FlightManagement.StartingAirPortJsonView,
            FlightManagement.DestinationAirPortJsonView,
            FlightManagement.PlaneJsonView,
            Plane.AirlineCompanyJsonView,
            Plane.ShipSpaceJsonView,
            FlightManagement.StartingPlaceJsonView,
            FlightManagement.TicketPricesJsonView,
            FlightManagement.TicketOrdersJsonView {
    }

}
