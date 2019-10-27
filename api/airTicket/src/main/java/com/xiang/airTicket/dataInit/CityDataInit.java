package com.xiang.airTicket.dataInit;

import com.alibaba.fastjson.JSON;
import com.xiang.airTicket.entity.City;
import com.xiang.airTicket.repository.CityRepository;
import com.xiang.airTicket.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
public class CityDataInit extends DataInit {

    @Autowired
    CityService cityService;
    @Autowired
    CityRepository cityRepository;

    static String path = "classpath:data/city.json";

    // 初始化城市数据
    @Override
    public void init() {
        if (!cityRepository.findAll().iterator().hasNext()) {
            File file = null;
            try {
                file = ResourceUtils.getFile(path);
                String content = new String(Files.readAllBytes(file.toPath()));
                List<City> cityList = JSON.parseArray(content, City.class);
                cityService.saveAll(cityList);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
