package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.Plane;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaneService {

    // 保存
    Plane save(Plane plane);

    // 通过名称获取分页数据
    Page<Plane> pageByName(String name, Pageable pageable);

    // 删除
    void delete(Long id);

    // 更新
    Plane update(Long id, Plane plane);

    // 通过id获取
    Plane getById(Long id);

    // 通过航空公司获取
    List<Plane> getByAirlineCompany(Long id);

}
