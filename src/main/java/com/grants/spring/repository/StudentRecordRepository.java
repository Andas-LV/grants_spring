package com.grants.spring.repository;

import com.grants.spring.model.StudentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRecordRepository extends JpaRepository<StudentRecord, Long> {
    // Можно добавить дополнительные запросы, если нужно
}