package com.xiang.airTicket.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.entity.AirlineCompany;
import com.xiang.airTicket.service.AirlineCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("airlineCompany")
public class AirlineCompanyController {

    @Autowired
    AirlineCompanyService airlineCompanyService;

    //  保存
    @PostMapping
    @JsonView(BaseJsonView.class)
    public AirlineCompany save(@RequestBody AirlineCompany airlineCompany) {
        return airlineCompanyService.save(airlineCompany);
    }

    // 更新
    @PutMapping("/{id}")
    @JsonView(BaseJsonView.class)
    public AirlineCompany update(@PathVariable Long id, @RequestBody AirlineCompany airlineCompany) {
        return airlineCompanyService.update(id, airlineCompany);
    }

    // 删除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        airlineCompanyService.delete(id);
    }

    //通过id获取
    @GetMapping("/{id}")
    @JsonView(BaseJsonView.class)
    public AirlineCompany getById(@PathVariable Long id) {
        return airlineCompanyService.getById(id);
    }

    // 名称获取分页数据
    @GetMapping("/getByName")
    @JsonView(BaseJsonView.class)
    public Page<AirlineCompany> pageByName(@RequestParam(required = false, name = "name") String name,
                                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return airlineCompanyService.pageByName(name, pageable);
    }

    private interface BaseJsonView extends AirlineCompany.CityJsonView {
    }

}
