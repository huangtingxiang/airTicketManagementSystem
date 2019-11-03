package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.AirPort;
import com.xiang.airTicket.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface AirPortRepository extends PagingAndSortingRepository<AirPort, Long> {
    Page<AirPort> findAllByNameLike(String name, Pageable pageable);

    List<AirPort> findAllByCity(City city);
}
