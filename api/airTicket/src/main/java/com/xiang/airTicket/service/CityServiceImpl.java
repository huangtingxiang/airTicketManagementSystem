package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.City;
import com.xiang.airTicket.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    public List<City> saveAll(Iterable<City> cities) {
        return (List<City>) cityRepository.saveAll(cities);
    }

    @Override
    public Iterable<City> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public City getById(Long id) {
        return cityRepository.findById(id).get();
    }

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City update(Long id, City city) {
        City oldCity = cityRepository.findById(id).get();
        oldCity.setName(city.getName());
        oldCity.setPrimaried(city.getPrimaried());
        oldCity.setPinyin(city.getPinyin());
        return cityRepository.save(city);
    }

    @Override
    public void delete(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public void setToPrimaried(Long id) {
        City city = cityRepository.findById(id).get();
        city.setPrimaried(true);
        cityRepository.save(city);
    }

    @Override
    public void setToNotPrimaried(Long id) {
        City city = cityRepository.findById(id).get();
        city.setPrimaried(false);
        cityRepository.save(city);

    }

    @Override
    public void setAllToPrimaried(List<Long> ids) {
        Iterable<City> cities = cityRepository.findAllById(ids);
        for (City city :
                cities) {
            city.setPrimaried(true);
        }
        cityRepository.saveAll(cities);
    }

    @Override
    public void setAllToNotPrimaried(List<Long> ids) {
        Iterable<City> cities = cityRepository.findAllById(ids);
        for (City city :
                cities) {
            city.setPrimaried(false);
        }
        cityRepository.saveAll(cities);
    }
}
