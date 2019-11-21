package com.jdxiang.airTicket.httpService;

import com.jdxiang.airTicket.entity.City;


public class CityService {

    BaseHttpService httpService = new BaseHttpService();

    /**
     * 获取所有城市
     * @param callBack
     */
    public void getAll(BaseHttpService.CallBack<City[]> callBack) {
        httpService.get("city", callBack, City[].class);
    }

}
