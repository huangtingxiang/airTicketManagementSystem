package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.Plane;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlaneRepository extends PagingAndSortingRepository<Plane, Long> {

    Page<Plane> findAllByNameLike(String name, Pageable pageable);
}
