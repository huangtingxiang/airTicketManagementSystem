package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.AirlineCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineCompanyService {

    // 保存
    AirlineCompany save(AirlineCompany airlineCompany);

    // 更新
    AirlineCompany update(Long id, AirlineCompany airlineCompany);

    // 删除
    void delete(Long id);

    // 通过名称分页
    Page<AirlineCompany> pageByName(String name, Pageable pageable);

    // 通过id获取
    AirlineCompany getById(Long id);

    // 获取全部
    List<AirlineCompany> getAll();
}
