package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.TransactionRecord;
import com.xiang.airTicket.entity.Visitor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TransactionRecordRepository extends PagingAndSortingRepository<TransactionRecord, Long> {


    List<TransactionRecord> findAllByVisitorOrderByIdDesc(Visitor visitor);
}
