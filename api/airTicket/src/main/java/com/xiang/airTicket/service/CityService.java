package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.City;

import java.util.List;

public interface CityService {
    // 保存全部城市
    List<City> saveAll(Iterable<City> cities);

    // 获取全部城市
    Iterable<City> getAll();

    // 获取指定城市
    City getById(Long id);

    // 保存城市
    City save(City city);

    // 更新城市
    City update(Long id, City city);

    // 删除城市
    void delete(Long id);

    // 设为主要城市
    void setToPrimaried(Long id);

    void setToNotPrimaried(Long id);

    // 全部设为主要城市
    void setAllToPrimaried(List<Long> ids);

    // 全部取消设为主要城市
    void setAllToNotPrimaried(List<Long> ids);
}
