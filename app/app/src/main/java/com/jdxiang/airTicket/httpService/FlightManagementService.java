package com.jdxiang.airTicket.httpService;

import android.util.Pair;

import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.entity.Page;

/**
 * 航班服务
 */
public class FlightManagementService {
    static FlightManagementService flightManagementService;

    public static FlightManagementService getInstance() {
        if (flightManagementService == null) {
            flightManagementService = new FlightManagementService();
        }
        return flightManagementService;
    }

    BaseHttpService httpService = new BaseHttpService();

    /**
     * 查找航班
     * @param startPlaceId 起始地
     * @param destinationId 目的地
     * @param searchTime 起飞时间
     * @param callBack
     */
    public void searchFlight(Long startPlaceId, Long destinationId, Long searchTime, BaseHttpService.CallBack callBack) {
        Page<FlightManagement> page = new Page<>();
        this.httpService.get("flightManagement/searchFlight",
                callBack,
                FlightManagement[].class,
                new Pair<>("startingPlaceId", startPlaceId.toString()),
                new Pair<>("destinationId", destinationId.toString()),
                new Pair<>("startTime", searchTime.toString()));
    }


}
