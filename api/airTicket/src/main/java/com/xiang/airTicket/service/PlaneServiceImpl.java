package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.Plane;
import com.xiang.airTicket.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaneServiceImpl implements PlaneService {

    @Autowired
    PlaneRepository planeRepository;

    @Override
    public Plane save(Plane plane) {
        return planeRepository.save(plane);
    }
}
