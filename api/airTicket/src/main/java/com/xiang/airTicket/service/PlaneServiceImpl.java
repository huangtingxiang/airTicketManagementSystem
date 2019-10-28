package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.Plane;
import com.xiang.airTicket.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlaneServiceImpl implements PlaneService {

    @Autowired
    PlaneRepository planeRepository;

    @Override
    public Plane save(Plane plane) {
        return planeRepository.save(plane);
    }

    @Override
    public Page<Plane> pageByName(String name, Pageable pageable) {
        return planeRepository.findAllByNameLike("%" + name + "%", pageable);
    }

    @Override
    public void delete(Long id) {
        planeRepository.deleteById(id);
    }

    @Override
    public Plane update(Long id, Plane plane) {
        Plane oldPlane = planeRepository.findById(id).get();
        oldPlane.setName(plane.getName());
        oldPlane.setIcon(plane.getIcon());
        oldPlane.setPlaneType(plane.getPlaneType());
        oldPlane.setAirlineCompany(plane.getAirlineCompany());
        oldPlane.setShipSpaces(plane.getShipSpaces());
        oldPlane.setTotalCol(plane.getTotalCol());
        oldPlane.setTotalRow(plane.getTotalRow());
        return planeRepository.save(oldPlane);
    }

    @Override
    public Plane getById(Long id) {
        return planeRepository.findById(id).get();
    }
}
