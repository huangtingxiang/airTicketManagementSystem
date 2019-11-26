package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.TransactionRecord;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRecordRepository extends PagingAndSortingRepository<TransactionRecord, Long> {
}
