package com.technicalTest.technicalTest.repository;

import com.technicalTest.technicalTest.model.CdrLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdrLogRepository extends JpaRepository<CdrLog, Long> {
    boolean existsByFileName(String fileName);
}