package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.AirPort;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirPortService {

    // 保存
    AirPort save(AirPort airPort);

    // 根据名称获取分页信息
    Page<AirPort> pageByName(String name, Pageable pageable);

    // 编辑
    AirPort update(Long id, AirPort airPort);

    // 通过id获取
    AirPort getById(Long id);

    // 删除
    void delete(Long id);
}
