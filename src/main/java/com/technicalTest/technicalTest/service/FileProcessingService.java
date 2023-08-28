package com.technicalTest.technicalTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technicalTest.technicalTest.repository.CdrLogRepository;
import com.technicalTest.technicalTest.repository.CallDetailRecordRepository;

import com.technicalTest.technicalTest.model.CdrLog;
import com.technicalTest.technicalTest.model.CallDetailRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;

@Service
public class FileProcessingService {

    @Autowired
    private CdrLogRepository cdrLogRepository;

    @Autowired
    private CallDetailRecordRepository callDetailRecordRepository;

    public void processFiles() {

        System.out.println("START PROCESS");
        String folderPath = "D:\\Users\\roberto.calero\\Documents\\NicCoders\\test";
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid folder path");
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files to process");
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                processSingleFile(file);
            }
        }
    }

    private void processSingleFile(File file) {
        String fileName = file.getName();

        // Check if the file has already been processed
        if (cdrLogRepository.existsByFileName(fileName)) {
            System.out.println("The file has already been processed");
            return;
        }

        // Create a log in the cdr_logs table
        CdrLog cdrLog = new CdrLog();
        cdrLog.setFileName(fileName);
        cdrLog.setStartTime(new Timestamp(System.currentTimeMillis()));
        cdrLog.setRecordsLoaded(0); // Initialize to 0 loaded records
        cdrLog.setRecordsFailed(0); // Initialize to 0 failed records
        cdrLogRepository.save(cdrLog); // save

        int recordsLoaded = 0;
        int recordsFailed = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (processSingleRecord(line)) {
                    recordsLoaded++;
                } else {
                    recordsFailed++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        cdrLog.setRecordsLoaded(recordsLoaded);
        cdrLog.setRecordsFailed(recordsFailed);
        cdrLog.setEndTime(new Timestamp(System.currentTimeMillis()));

        cdrLogRepository.save(cdrLog); // Update the record in the database
        System.out.println("END PROCESS");
    }

    private boolean processSingleRecord(String line) {
        try {
            String[] fields = line.split("\\|");

            // Instantiate CallDetailRecord and assign values to fields
            CallDetailRecord cdr = new CallDetailRecord();

            // replace , por punto fields[0]
            String recordDateText = fields[0].replace(",", ".");
            LocalDateTime recordDate = LocalDateTime.parse(recordDateText,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]"));
            cdr.setRecordDate(recordDate);

            cdr.setLSpc(fields[1] == null || fields[1].isEmpty() ? null : Integer.parseInt(fields[1]));
            cdr.setLSsn(fields[2] == null || fields[2].isEmpty() ? null : Integer.parseInt(fields[2]));
            cdr.setLRi(fields[3] == null || fields[3].isEmpty() ? null : Integer.parseInt(fields[3]));
            cdr.setLGtI(fields[4] == null || fields[4].isEmpty() ? null : Integer.parseInt(fields[4]));
            cdr.setLGtDigits(fields[5]);
            cdr.setRSpc(fields[6] == null || fields[6].isEmpty() ? null : Integer.parseInt(fields[6]));
            cdr.setRSsn(fields[7] == null || fields[7].isEmpty() ? null : Integer.parseInt(fields[7]));
            cdr.setRRi(fields[8] == null || fields[8].isEmpty() ? null : Integer.parseInt(fields[8]));
            cdr.setRGtI(fields[9] == null || fields[9].isEmpty() ? null : Integer.parseInt(fields[9]));
            cdr.setRGtDigits(fields[10]);
            cdr.setServiceCode(fields[11]);
            cdr.setOrNature(fields[12] == null || fields[12].isEmpty() ? null : Integer.parseInt(fields[12]));
            cdr.setOrPlan(fields[13] == null || fields[13].isEmpty() ? null : Integer.parseInt(fields[13]));
            cdr.setOrDigits(fields[14]);
            cdr.setDeNature(fields[15] == null || fields[15].isEmpty() ? null : Integer.parseInt(fields[15]));
            cdr.setDePlan(fields[16] == null || fields[16].isEmpty() ? null : Integer.parseInt(fields[16]));
            cdr.setDeDigits(fields[17]);
            cdr.setIsdnNature(fields[18] == null || fields[18].isEmpty() ? null : Integer.parseInt(fields[18]));
            cdr.setIsdnPlan(fields[19] == null || fields[19].isEmpty() ? null : Integer.parseInt(fields[19]));
            cdr.setMsisdn(fields[20]);
            cdr.setVlrNature(fields[21] == null || fields[21].isEmpty() ? null : Integer.parseInt(fields[21]));
            cdr.setVlrPlan(fields[22] == null || fields[22].isEmpty() ? null : Integer.parseInt(fields[22]));
            cdr.setVlrDigits(fields[23]);
            cdr.setImsi(fields[24]);
            cdr.setStatus(fields[25]);
            cdr.setType(fields[26]);

            // replace , por punto fields[27]
            String tstampText = fields[27].replace(",", ".");
            LocalDateTime tstamp = LocalDateTime.parse(tstampText,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]"));

            cdr.setTstamp(tstamp);

            cdr.setLocalDialogId(fields[28] == null || fields[28].isEmpty() ? null : Long.parseLong(fields[28]));
            cdr.setId(fields[29]);

            // Save the instance in the database
            callDetailRecordRepository.save(cdr);

            return true; // successfully processed
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Processing error
        }
    }
}