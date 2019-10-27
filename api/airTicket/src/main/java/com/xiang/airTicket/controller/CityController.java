package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.City;
import com.xiang.airTicket.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("city")
public class CityController {

    @Autowired
    CityService cityService;

    // 获取所有的城市
    @GetMapping
    @JsonView(BaseJsonView.class)
    public Iterable<City> getAll() {
        return cityService.getAll();
    }

    // 保存所有的城市
    @PostMapping("/saveAll")
    @JsonView(BaseJsonView.class)
    public Iterable<City> saveAll(@RequestBody List<City> cities) {
        return cityService.saveAll(cities);
    }

    // 通过id获取
    @GetMapping("/{id}")
    @JsonView(BaseJsonView.class)
    public City getById(@PathVariable Long id) {
        return cityService.getById(id);
    }

    // 保存城市
    @PostMapping
    @JsonView(BaseJsonView.class)
    public City save(@RequestBody City city) {
        return cityService.save(city);
    }

    // 删除城市
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cityService.delete(id);
    }

    // 设为热门城市
    @PutMapping("/toPrimaried/{id}")
    public void toPrimaried(@PathVariable Long id) {
        cityService.setToPrimaried(id);
    }

    // 全部设为热门城市
    @PostMapping("/allToPrimaried")
    public void allToPrimaried(@RequestBody List<Long> ids) {
        cityService.setAllToPrimaried(ids);
    }

    // 取消设为热门城市
    @PutMapping("/notToPrimaried/{id}")
    public void notToPrimaried(@PathVariable Long id) {
        cityService.setToNotPrimaried(id);
    }

    // 取消设为热门城市
    @PostMapping("/allNotToPrimaried")
    public void allNotToPrimaried(@RequestBody List<Long> ids) {
        cityService.setAllToNotPrimaried(ids);
    }

    // 更新
    @PutMapping("/{id}")
    @JsonView(BaseJsonView.class)
    public City getById(@PathVariable Long id, @RequestBody City city) {
        return cityService.update(id, city);
    }


    private interface BaseJsonView {

    }
}
