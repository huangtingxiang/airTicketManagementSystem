package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.Plane;
import com.xiang.airTicket.entity.ShipSpace;
import com.xiang.airTicket.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("plane")
public class PlaneController {

    @Autowired
    PlaneService planeService;

    // 保存
    @PostMapping
    @JsonView(BaseJsonView.class)
    public Plane save(@RequestBody Plane plane) {
        return planeService.save(plane);
    }

    private interface BaseJsonView extends Plane.AirlineCompanyJsonView, Plane.ShipSpaceJsonView, ShipSpace.SeatJsonView {
    }

}
