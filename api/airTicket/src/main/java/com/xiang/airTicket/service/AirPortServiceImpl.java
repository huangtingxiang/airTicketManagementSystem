package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.AirPort;
import com.xiang.airTicket.repository.AirPortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class AirPortServiceImpl implements AirPortService {

    @Autowired
    AirPortRepository airPortRepository;

    @Override
    public AirPort save(AirPort airPort) {
        return airPortRepository.save(airPort);
    }

    @Override
    public Page<AirPort> pageByName(String name, Pageable pageable) {
        return airPortRepository.findAllByNameLike("%" + name + "%", pageable);
    }

    @Override
    public AirPort update(Long id, AirPort airPort) {
        AirPort oldAirPort = airPortRepository.findById(id).get();
        oldAirPort.setCity(airPort.getCity());
        oldAirPort.setName(airPort.getName());
        oldAirPort.setIcon(airPort.getIcon());
        return airPortRepository.save(oldAirPort);
    }

    @Override
    public AirPort getById(Long id) {
        return airPortRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        airPortRepository.deleteById(id);
    }
}
