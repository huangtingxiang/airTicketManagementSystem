package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.AirlineCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface AirlineCompanyRepository extends PagingAndSortingRepository<AirlineCompany, Long> {

    Page<AirlineCompany> findAllByNameLike(String name, Pageable pageable);

}
