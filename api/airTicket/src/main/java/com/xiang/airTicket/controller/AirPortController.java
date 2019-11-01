package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.xiang.airTicket.entity.AirPort;
import com.xiang.airTicket.service.AirPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("airPort")
public class AirPortController {

    @Autowired
    AirPortService airPortService;

    // 保存
    @PostMapping
    @JsonView(BaseJsonView.class)
    public AirPort save(@RequestBody AirPort airPort) {
        return airPortService.save(airPort);
    }

    // 更新
    @PutMapping("/{id}")
    @JsonView(BaseJsonView.class)
    public AirPort update(@PathVariable Long id, @RequestBody AirPort airPort) {
        return airPortService.update(id, airPort);
    }

    // 根据名称获取分页数据
    @GetMapping("/pageByName")
    @JsonView(BaseJsonView.class)
    public Page<AirPort> pageByName(@RequestParam(required = false, name = "name") String name,
                                    @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return airPortService.pageByName(name, pageable);
    }

    // 删除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        airPortService.delete(id);
    }

    @GetMapping("{id}")
    @JsonView(BaseJsonView.class)
    public AirPort getById(@PathVariable Long id) {
        return airPortService.getById(id);
    }

    // 通过城市id获取
    @GetMapping("/getByCity/{id}")
    @JsonView(BaseJsonView.class)
    public List<AirPort> getByCity(@PathVariable Long id) {
        return airPortService.findByCity(id);
    }

    private interface BaseJsonView extends AirPort.CityJsonView {
    }
}
