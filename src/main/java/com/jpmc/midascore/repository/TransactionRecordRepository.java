package com.jpmc.midascore.repository; // Ensure this matches your actual package name

import com.jpmc.midascore.entity.TransactionRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRecordRepository extends CrudRepository<TransactionRecord, Long> {
}