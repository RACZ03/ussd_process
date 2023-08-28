package com.technicalTest.technicalTest.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cdr_logs")
public class CdrLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "records_loaded")
    private int recordsLoaded;

    @Column(name = "records_failed")
    private int recordsFailed;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getRecordsLoaded() {
        return recordsLoaded;
    }

    public void setRecordsLoaded(int recordsLoaded) {
        this.recordsLoaded = recordsLoaded;
    }

    public int getRecordsFailed() {
        return recordsFailed;
    }

    public void setRecordsFailed(int recordsFailed) {
        this.recordsFailed = recordsFailed;
    }
}