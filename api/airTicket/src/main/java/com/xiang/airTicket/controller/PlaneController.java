package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.Plane;
import com.xiang.airTicket.entity.ShipSpace;
import com.xiang.airTicket.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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

    // 通过id获取
    @GetMapping("/{id}")
    @JsonView(BaseJsonView.class)
    public Plane getById(@PathVariable Long id) {
        return planeService.getById(id);
    }

    // 通过名称获取
    @GetMapping("/pageByName")
    @JsonView(BaseJsonView.class)
    public Page<Plane> pageByName(@RequestParam(required = false, name = "name") String name,
                                  @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return planeService.pageByName(name, pageable);
    }

    // 更新
    @PutMapping("/{id}")
    @JsonView(BaseJsonView.class)
    public Plane delete(@PathVariable Long id, @RequestBody Plane plane) {
        return planeService.update(id, plane);
    }

    // 删除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        planeService.delete(id);
    }

    private interface BaseJsonView extends Plane.AirlineCompanyJsonView, Plane.ShipSpaceJsonView, ShipSpace.SeatJsonView {
    }

}
