package com.jdxiang.airTicket.httpService;

import android.util.Pair;

import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.entity.Page;

import java.util.ArrayList;
import java.util.List;

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
     *
     * @param startPlaceId  起始地
     * @param destinationId 目的地
     * @param searchTime    起飞时间
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

    public void searchFlightByPage(Long startPlaceId, Long destinationId, Long searchTime, BaseHttpService.CallBack callBack) {
        List<Pair<String, String>> pairs = new ArrayList<>();
        if (startPlaceId != null) {
            pairs.add(new Pair<>("startingPlaceId", startPlaceId.toString()));
        }
        if (destinationId != null) {
            pairs.add(new Pair<>("destinationId", destinationId.toString()));
        }
        if (searchTime != null) {
            pairs.add(new Pair<>("startTime", searchTime.toString()));
        }
        pairs.add(new Pair<>("page", "0"));
        pairs.add(new Pair<>("size", "5"));
        this.httpService.get("flightManagement/pageContent",
                callBack,
                FlightManagement[].class, pairs.toArray(new Pair[pairs.size()]));
    }


}
