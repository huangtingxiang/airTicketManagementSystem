package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.FlightManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface FlightManagementService {

    // 保存
    FlightManagement save(FlightManagement flightManagement);

    //  通过始发地 目的地 起飞日期获取分页数据
    Page<FlightManagement> pageByStartingPlaceAndDestinationStartTime(
            Long startingPlaceId,
            Long destinationId,
            Date startTime,
            Pageable pageable
    );

    //  通过始发地 目的地 起飞日期获取分页数据 没有结束日期限制
    Page<FlightManagement> pageByStartingPlaceAndDestinationStartTimeGetContent(
            Long startingPlaceId,
            Long destinationId,
            Date startTime,
            Pageable pageable
    );

    List searchFlight(Long startingPlaceId,
                      Long destinationId,
                      Date startTime);

    // 更新
    FlightManagement update(Long id, FlightManagement flightManagement);

    // 通过id获取
    FlightManagement getById(Long id);

    // 删除
    void delete(Long id);

}
