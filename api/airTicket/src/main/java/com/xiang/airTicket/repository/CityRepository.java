package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.City;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CityRepository extends PagingAndSortingRepository<City, Long> {
}
