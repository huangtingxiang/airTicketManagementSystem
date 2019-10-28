package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.AirlineCompany;
import com.xiang.airTicket.repository.AirlineCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AirlineCompanyServiceImpl implements AirlineCompanyService {

    @Autowired
    AirlineCompanyRepository airlineCompanyRepository;

    @Override
    public AirlineCompany save(AirlineCompany airlineCompany) {
        return airlineCompanyRepository.save(airlineCompany);
    }

    @Override
    public AirlineCompany update(Long id, AirlineCompany airlineCompany) {
        AirlineCompany oldAirlineCompany = airlineCompanyRepository.findById(id).get();
        oldAirlineCompany.setName(airlineCompany.getName());
        oldAirlineCompany.setIcon(airlineCompany.getIcon());
        oldAirlineCompany.setCity(airlineCompany.getCity());
        return airlineCompanyRepository.save(oldAirlineCompany);
    }

    @Override
    public void delete(Long id) {
        airlineCompanyRepository.deleteById(id);
    }

    @Override
    public Page<AirlineCompany> pageByName(String name, Pageable pageable) {
        return airlineCompanyRepository.findAllByNameLike("%" + name + "%", pageable);
    }

    @Override
    public AirlineCompany getById(Long id) {
        return airlineCompanyRepository.findById(id).get();
    }
}
