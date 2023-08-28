package com.technicalTest.technicalTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.technicalTest.technicalTest.service.FileProcessingService;

@RestController
@RequestMapping("/process")
public class FileProcessingController {

    @Autowired
    private FileProcessingService fileProcessingService;

    @PostMapping("/start")
    public ResponseEntity<String> startFileProcessing() {
        fileProcessingService.processFiles(); // Call processing logic
        return ResponseEntity.ok("File processing started.");
    }
}