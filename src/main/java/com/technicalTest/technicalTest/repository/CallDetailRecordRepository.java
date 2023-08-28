package com.technicalTest.technicalTest.repository;

import com.technicalTest.technicalTest.model.CallDetailRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallDetailRecordRepository extends JpaRepository<CallDetailRecord, Long> {
    // Puedes agregar métodos de consulta adicionales si es necesario
}