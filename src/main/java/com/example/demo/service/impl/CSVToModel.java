package com.example.demo.service.impl;

import com.example.demo.domain.ParkingTransaction;
import com.example.demo.repository.ParkingTransactionRepository;
import com.example.demo.service.CSVToModelService;
import com.example.demo.service.dto.CSVdto;
import com.example.demo.service.dto.XLSXdto;
import com.example.demo.service.dto.XLSdto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVToModel implements CSVToModelService {

    /*
    * fileType 1 = XLS
    * fileType 2 = XLSX
    * fileType 3 = CSV
    */

    private final ParkingTransactionRepository parkingTransactionRepository;

    final Logger log = Logger.getLogger(CSVToModel.class.getName());

    public CSVToModel(ParkingTransactionRepository parkingTransactionRepository) {
        this.parkingTransactionRepository = parkingTransactionRepository;
    }

    @Override
    public Boolean readCSV(Integer fileType, String inputFilePath) throws Exception {

        String extType = FilenameUtils.getExtension(inputFilePath);
        log.debug("" + inputFilePath);
        log.debug("" + extType);

        FileInputStream fis = new FileInputStream(inputFilePath);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("ddMMyyyyhhmmss");
        DateTimeFormatter formatter3 = new DateTimeFormatterBuilder()
                .appendPattern("[dd/MM HH:mm:ss][d[d]/M[M]/yy H[H]:mm]")
                .optionalStart()
                .parseDefaulting(ChronoField.YEAR,2018)
                .optionalEnd()
                .toFormatter();
        DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("H[H]:mm");
        DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("M[M]/d[d]/yyyy H[H]:mm");
//        DateTimeFormatter formatter6 = DateTimeFormatter.ofPattern("[H]H:mm");

        try{
            if (!extType.equals("csv")) {
                throw new Exception("Invalid data type");
            }

             if ("csv".equals(extType)) {
                 //XLS format
                 if(fileType == 1) {
                     Reader reader = new BufferedReader(new FileReader(inputFilePath));
                     CsvToBean<XLSdto> csvReader = new CsvToBeanBuilder(reader)
                             .withType(XLSdto.class)
                             .withSeparator(',')
                             .withIgnoreLeadingWhiteSpace(true)
                             .withIgnoreEmptyLine(true)
                             .build();
                     List<XLSdto> XLSdtoList = new ArrayList<XLSdto>();
                     XLSdtoList = csvReader.parse();
                     log.debug("" + XLSdtoList.size());
                     log.debug("" + XLSdtoList);
//                     List<ParkingTransaction> parkingTransactions = new ArrayList<ParkingTransaction>();

                     for (int i = 0; i < XLSdtoList.size(); i++) {
                         log.debug("saving to db");
                        ParkingTransaction parkingTransaction = new ParkingTransaction();

                         String entry = XLSdtoList.get(i).getEntryTime();
                         log.debug("entry time: "+XLSdtoList.get(i).getEntryTime());
                         ZonedDateTime entryTime = ZonedDateTime.now();
                         String exit = XLSdtoList.get(i).getExitTime();
                         log.debug("exit time: "+XLSdtoList.get(i).getExitTime());
                         ZonedDateTime exitTime = ZonedDateTime.now();
                         String parkedTime = XLSdtoList.get(i).getParkedTime();
                         if(StringUtils.isEmpty(entry) && StringUtils.isNotEmpty(exit)){
                             if(StringUtils.isEmpty(parkedTime)) {
                                entry=exit;
                                parkedTime="0";
                                 entryTime = ZonedDateTime.parse(entry, formatter.withZone(ZoneId.systemDefault()));
                                 log.debug("converted entry time : "+entryTime);
                             } else {
                                 entryTime = ZonedDateTime.parse(exit, formatter.withZone(ZoneId.systemDefault())).minusMinutes(Long.parseLong(parkedTime));
                             }
                             exitTime = ZonedDateTime.parse(exit, formatter.withZone(ZoneId.systemDefault()));
                             log.debug("converted exit time : "+exitTime);
                         } else if(StringUtils.isEmpty(exit) && StringUtils.isNotEmpty(entry)){
                             if(StringUtils.isEmpty(parkedTime)) {
                                 exit=entry;
                                 parkedTime="0";
                                 exitTime = ZonedDateTime.parse(exit, formatter.withZone(ZoneId.systemDefault()));
                                 log.debug("converted exit time : " + exitTime);
                             } else {
                                 exitTime = ZonedDateTime.parse(entry, formatter.withZone(ZoneId.systemDefault())).plusMinutes(Long.parseLong(parkedTime));
                             }
                             entryTime = ZonedDateTime.parse(entry, formatter.withZone(ZoneId.systemDefault()));
                             log.debug("converted entry time : " + exitTime);
                         } else {
                             entryTime = ZonedDateTime.parse(entry, formatter.withZone(ZoneId.systemDefault()));
                             log.debug("converted entry time : "+entryTime);

                             exitTime = ZonedDateTime.parse(exit, formatter.withZone(ZoneId.systemDefault()));
                             log.debug("converted exit time : " + exitTime);
                         }

                        parkingTransaction.setEntryDateTime(entryTime);
                        parkingTransaction.setExitDateTime(exitTime);
                        parkingTransaction.setDuration(Integer.parseInt(parkedTime));
                        parkingTransaction.setPaymentAmount((long)Double.parseDouble(XLSdtoList.get(i).getPaid())*100L);
                        parkingTransaction.setTranAmount((long)Double.parseDouble(XLSdtoList.get(i).getPaid())*100L);
                        parkingTransaction.setVehicleType(XLSdtoList.get(i).getParkingType());
                        parkingTransaction.setEntrySessionId(XLSdtoList.get(i).getIuNo()+parkingTransaction.getEntryDateTime().format(formatter2));
                        parkingTransaction.setExitSessionId(XLSdtoList.get(i).getIuNo()+parkingTransaction.getExitDateTime().format(formatter2));
                        parkingTransaction.setCarParkId(1500L);
                        String carPlateNo = XLSdtoList.get(i).getVehicleNo();
                        parkingTransaction.setCarPlateNo(StringUtils.isEmpty(carPlateNo) ? "No Car Plate" : carPlateNo);
                        parkingTransaction.setCreatedDate(ZonedDateTime.now());
                        parkingTransaction.setCreatedBy("xlsadmin");
                        parkingTransaction.setLastModifiedDate(ZonedDateTime.now());
                        parkingTransaction.setLastModifiedBy("xlsadmin");

                        log.debug("Before saving to DB : " + parkingTransaction);
                        //TODO check for duplicates, if yes then update
                        List<ParkingTransaction> parkingTransactionList = new ArrayList<ParkingTransaction>();
                        parkingTransactionList = parkingTransactionRepository.findByEntrySessionIdAndExitSessionId(parkingTransaction.getEntrySessionId(),parkingTransaction.getExitSessionId());
                        if(parkingTransactionList.size()>0){
                            log.debug("parkingTransactionList : " + parkingTransactionList);
                            parkingTransaction.setId(parkingTransactionList.get(0).getId());
                            parkingTransaction.setCreatedBy(parkingTransactionList.get(0).getCreatedBy());
                            parkingTransaction.setCreatedDate(parkingTransactionList.get(0).getCreatedDate());
                        }
                        parkingTransactionRepository.save(parkingTransaction);

//                        parkingTransactions.add(parkingTransaction);
                     }

//                 log.debug("parkingTransactions :" + parkingTransactions);
                 }

                 //XLSX format
               if(fileType == 2) {
                     Reader reader = new BufferedReader(new FileReader(inputFilePath));
                     CsvToBean<XLSXdto> csvReader = new CsvToBeanBuilder(reader)
                             .withType(XLSXdto.class)
                             .withSeparator(',')
                             .withIgnoreLeadingWhiteSpace(true)
                             .withIgnoreEmptyLine(true)
                             .build();
                     List<XLSXdto> XLSXdtoList = new ArrayList<XLSXdto>();
                     XLSXdtoList = csvReader.parse();
                     log.debug("" + XLSXdtoList.size());
                     log.debug("" + XLSXdtoList);

                     for (int i = 0; i < XLSXdtoList.size(); i++) {
                         log.debug("saving to db");
                        ParkingTransaction parkingTransaction = new ParkingTransaction();

                         Long dayMinutes = 0L;
                         Long diffMinutes = 0L;
                         String entry = XLSXdtoList.get(i).getEntryTime();
                         log.debug("entry time: "+XLSXdtoList.get(i).getEntryTime());
                         ZonedDateTime entryTime = ZonedDateTime.now();
                         String exit = XLSXdtoList.get(i).getExitTime();
                         log.debug("exit time: "+XLSXdtoList.get(i).getExitTime());
                         ZonedDateTime exitTime = ZonedDateTime.now();
                         String parkedTime = XLSXdtoList.get(i).getParkedTime();
                         if(StringUtils.isNotEmpty(parkedTime)){
                             log.info("Index : " + parkedTime.lastIndexOf(" "));
                             if(parkedTime.lastIndexOf(" ")!=-1){
                                 String parkedTimeDay =parkedTime.substring(0, parkedTime.lastIndexOf(" "));
                                 log.debug("parkedTimeDay : "+parkedTimeDay);
                                 dayMinutes = Long.parseLong(parkedTimeDay)*60*24;
                                 log.debug("converted day to minutes : " + dayMinutes);
                                 parkedTime = parkedTime.substring(parkedTime.lastIndexOf(" ")+1);
                             };
                         } else {
                             parkedTime="0:00";
                         }

                         log.debug("parkedTime : "+parkedTime);

                         log.debug("dayMinutes : "+dayMinutes);

                         if(dayMinutes!=0L){
                             diffMinutes=dayMinutes;
                         }

                         log.debug("diffMinutes : "+diffMinutes);

                         if(StringUtils.isEmpty(entry) && StringUtils.isNotEmpty(exit)){
                             if(StringUtils.isEmpty(parkedTime)) {
                                 entry=exit;
                                 parkedTime="0";
                                 entryTime = ZonedDateTime.parse(entry, formatter3.withZone(ZoneId.systemDefault()));
                                 log.debug("converted entry time : "+entryTime);
                             } else {
                                 if(!parkedTime.equalsIgnoreCase("0")){
                                     log.debug("parkedTime11 : " + LocalTime.parse(parkedTime,formatter4));
                                     LocalTime parkedDuration = LocalTime.parse(parkedTime,formatter4);
                                     log.debug("parkedDuration11 : " + (parkedDuration.getHour()*60+parkedDuration.getMinute()));
                                     diffMinutes += (long)(parkedDuration.getHour()*60+parkedDuration.getMinute());
                                 } else {
                                     diffMinutes = 0L;
                                 }
                                 log.debug("diffMinutes1 : "+diffMinutes);
                                 entryTime = ZonedDateTime.parse(exit, formatter3.withZone(ZoneId.systemDefault())).minusMinutes(diffMinutes);
                             }
                             exitTime = ZonedDateTime.parse(exit, formatter3.withZone(ZoneId.systemDefault()));
                             log.debug("converted exit time : "+exitTime);
                         } else if(StringUtils.isEmpty(exit) && StringUtils.isNotEmpty(entry)){
                             if(StringUtils.isEmpty(parkedTime)) {
                                 exit=entry;
                                 parkedTime="0";
                                 exitTime = ZonedDateTime.parse(exit, formatter3.withZone(ZoneId.systemDefault()));
                                 log.debug("converted exit time : " + exitTime);
                             } else {
                                 if(!parkedTime.equalsIgnoreCase("0")){
                                     log.debug("parkedTime22 : " + LocalTime.parse(parkedTime,formatter4));
                                     LocalTime parkedDuration = LocalTime.parse(parkedTime,formatter4);
                                     log.debug("parkedDuration22 : " + (parkedDuration.getHour()*60+parkedDuration.getMinute()));
                                     diffMinutes += (long)(parkedDuration.getHour()*60+parkedDuration.getMinute());
                                 } else {
                                     diffMinutes = 0L;
                                 }
                                 log.debug("diffMinutes2 : "+diffMinutes);
                                 exitTime = ZonedDateTime.parse(entry, formatter3.withZone(ZoneId.systemDefault())).plusMinutes(diffMinutes);
                             }
                             entryTime = ZonedDateTime.parse(entry, formatter3.withZone(ZoneId.systemDefault()));
                             log.debug("converted entry time : " + exitTime);
                         } else {
                             entryTime = ZonedDateTime.parse(entry, formatter3.withZone(ZoneId.systemDefault()));
                             log.debug("converted entry time : "+entryTime);

                             exitTime = ZonedDateTime.parse(exit, formatter3.withZone(ZoneId.systemDefault()));
                             log.debug("converted exit time : " + exitTime);

                             if(!parkedTime.equalsIgnoreCase("0")){
                                 log.debug("parkedTime33 : " + LocalTime.parse(parkedTime,formatter4));
                                 LocalTime parkedDuration = LocalTime.parse(parkedTime,formatter4);
                                 log.debug("parkedDuration33 : " + (parkedDuration.getHour()*60+parkedDuration.getMinute()));
                                 diffMinutes += (long)(parkedDuration.getHour()*60+parkedDuration.getMinute());
                             } else {
                                 diffMinutes = 0L;
                             }
                             log.debug("diffMinutes3 : "+diffMinutes);
                         }

                         if(StringUtils.isEmpty(XLSXdtoList.get(i).getPaidAmt())){
                             XLSXdtoList.get(i).setPaidAmt("0");
                         }

                        parkingTransaction.setEntryDateTime(entryTime);
                        parkingTransaction.setExitDateTime(exitTime);
                        parkingTransaction.setDuration(Integer.valueOf(String.valueOf(diffMinutes)));
                        parkingTransaction.setPaymentAmount((long)Double.parseDouble(XLSXdtoList.get(i).getPaidAmt())*100L);
                        parkingTransaction.setTranAmount((long)Double.parseDouble(XLSXdtoList.get(i).getPaidAmt())*100L);
                        parkingTransaction.setVehicleType(XLSXdtoList.get(i).getType());
                        parkingTransaction.setCarPlateNo(XLSXdtoList.get(i).getVehicleNo());
                        parkingTransaction.setEntrySessionId(XLSXdtoList.get(i).getIuNo()+parkingTransaction.getEntryDateTime().format(formatter2));
                        parkingTransaction.setExitSessionId(XLSXdtoList.get(i).getIuNo()+parkingTransaction.getExitDateTime().format(formatter2));
                        parkingTransaction.setCarParkId(1500L);
                        String carPlateNo = XLSXdtoList.get(i).getVehicleNo();
                        parkingTransaction.setCarPlateNo(StringUtils.isEmpty(carPlateNo) ? "No Car Plate" : carPlateNo);
                        parkingTransaction.setCreatedDate(ZonedDateTime.now());
                        parkingTransaction.setCreatedBy("xlsxadmin");
                        parkingTransaction.setLastModifiedDate(ZonedDateTime.now());
                        parkingTransaction.setLastModifiedBy("xlsxadmin");

                        log.debug("Before saving to DB : " + parkingTransaction);
                        //TODO check for duplicates, if yes then update
                        List<ParkingTransaction> parkingTransactionList = new ArrayList<ParkingTransaction>();
                        parkingTransactionList = parkingTransactionRepository.findByEntrySessionIdAndExitSessionId(parkingTransaction.getEntrySessionId(),parkingTransaction.getExitSessionId());
                        if(parkingTransactionList.size()>0){
                            log.debug("parkingTransactionList : " + parkingTransactionList);
                            parkingTransaction.setId(parkingTransactionList.get(0).getId());
                            parkingTransaction.setCreatedBy(parkingTransactionList.get(0).getCreatedBy());
                            parkingTransaction.setCreatedDate(parkingTransactionList.get(0).getCreatedDate());
                        }
                        parkingTransactionRepository.save(parkingTransaction);
                     }
                 }

                 //CSV format
                 if(fileType == 3) {
                     Reader reader = new BufferedReader(new FileReader(inputFilePath));
                     CsvToBean<CSVdto> csvReader = new CsvToBeanBuilder(reader)
                             .withType(CSVdto.class)
                             .withSeparator(',')
                             .withIgnoreLeadingWhiteSpace(true)
                             .withIgnoreEmptyLine(true)
                             .build();
                     List<CSVdto> CSVdtoList = new ArrayList<CSVdto>();
                     CSVdtoList = csvReader.parse();
                     log.debug("" + CSVdtoList.size());
                     log.debug("" + CSVdtoList);

                     for (int i = 0; i < CSVdtoList.size(); i++) {
                         log.debug("saving to db");
                         ParkingTransaction parkingTransaction = new ParkingTransaction();

                         Long dayMinutes = 0L;
                         Long diffMinutes = 0L;
                         String entry = CSVdtoList.get(i).getEntryTime();
                         log.debug("entry time: "+CSVdtoList.get(i).getEntryTime());
                         ZonedDateTime entryTime = ZonedDateTime.now();
                         String exit = CSVdtoList.get(i).getExitTime();
                         log.debug("exit time: "+CSVdtoList.get(i).getExitTime());
                         ZonedDateTime exitTime = ZonedDateTime.now();
                         String parkedTime = CSVdtoList.get(i).getParkedTime();
                         if(StringUtils.isNotEmpty(parkedTime)){
                             log.info("Index : " + parkedTime.lastIndexOf(" "));
                             if(parkedTime.lastIndexOf(" ")!=-1){
                                 String parkedTimeDay =parkedTime.substring(0, parkedTime.lastIndexOf(" "));
                                 log.debug("parkedTimeDay : "+parkedTimeDay);
                                 dayMinutes = Long.parseLong(parkedTimeDay)*60*24;
                                 log.debug("converted day to minutes : " + dayMinutes);
                                 parkedTime = parkedTime.substring(parkedTime.lastIndexOf(" ")+1);
                             };

                         } else {
                             parkedTime="0:00";
                         }

                         log.debug("parkedTime : "+parkedTime);

                         log.debug("dayMinutes : "+dayMinutes);

                         if(dayMinutes!=0L){
                             diffMinutes=dayMinutes;
                         }

                         log.debug("diffMinutes : "+diffMinutes);

                         if(StringUtils.isEmpty(entry) && StringUtils.isNotEmpty(exit)){
                             if(StringUtils.isEmpty(parkedTime)) {
                                 entry=exit;
                                 parkedTime="0";
                                 entryTime = ZonedDateTime.parse(entry, formatter5.withZone(ZoneId.systemDefault()));
                                 log.debug("converted entry time : "+entryTime);
                             } else {
                                 if(!parkedTime.equalsIgnoreCase("0")){
                                     log.debug("parkedTime11 : " + LocalTime.parse(parkedTime,formatter4));
                                     LocalTime parkedDuration = LocalTime.parse(parkedTime,formatter4);
                                     log.debug("parkedDuration11 : " + (parkedDuration.getHour()*60+parkedDuration.getMinute()));
                                     diffMinutes += (long)(parkedDuration.getHour()*60+parkedDuration.getMinute());
                                 } else {
                                     diffMinutes = 0L;
                                 }
                                 log.debug("diffMinutes11 : "+diffMinutes);
                                 entryTime = ZonedDateTime.parse(exit, formatter5.withZone(ZoneId.systemDefault())).minusMinutes(diffMinutes);
                             }
                             exitTime = ZonedDateTime.parse(exit, formatter5.withZone(ZoneId.systemDefault()));
                             log.debug("converted exit time : "+exitTime);
                         } else if(StringUtils.isEmpty(exit) && StringUtils.isNotEmpty(entry)){
                             if(StringUtils.isEmpty(parkedTime)) {
                                 exit=entry;
                                 parkedTime="0";
                                 exitTime = ZonedDateTime.parse(exit, formatter5.withZone(ZoneId.systemDefault()));
                                 log.debug("converted exit time : " + exitTime);
                             } else {
                                 if(!parkedTime.equalsIgnoreCase("0")){
                                     log.debug("parkedTime22 : " + LocalTime.parse(parkedTime,formatter4));
                                     LocalTime parkedDuration = LocalTime.parse(parkedTime,formatter4);
                                     log.debug("parkedDuration22 : " + (parkedDuration.getHour()*60+parkedDuration.getMinute()));
                                     diffMinutes += (long)(parkedDuration.getHour()*60+parkedDuration.getMinute());
                                 } else {
                                     diffMinutes = 0L;
                                 }
                                 log.debug("diffMinutes22 : "+diffMinutes);
                                 exitTime = ZonedDateTime.parse(entry, formatter5.withZone(ZoneId.systemDefault())).plusMinutes(diffMinutes);
                             }
                             entryTime = ZonedDateTime.parse(entry, formatter5.withZone(ZoneId.systemDefault()));
                             log.debug("converted entry time : " + exitTime);
                         } else {
                             entryTime = ZonedDateTime.parse(entry, formatter5.withZone(ZoneId.systemDefault()));
                             log.debug("converted entry time : "+entryTime);

                             exitTime = ZonedDateTime.parse(exit, formatter5.withZone(ZoneId.systemDefault()));
                             log.debug("converted exit time : " + exitTime);

                             if(!parkedTime.equalsIgnoreCase("0")){
                                 log.debug("parkedTime33 : " + LocalTime.parse(parkedTime,formatter4));
                                 LocalTime parkedDuration = LocalTime.parse(parkedTime,formatter4);
                                 log.debug("parkedDuration33 : " + (parkedDuration.getHour()*60+parkedDuration.getMinute()));
                                 diffMinutes += (long)(parkedDuration.getHour()*60+parkedDuration.getMinute());
                             } else {
                                 diffMinutes = 0L;
                             }
                             log.debug("diffMinutes33 : "+diffMinutes);
                         }

                         parkingTransaction.setEntryDateTime(entryTime);
                         parkingTransaction.setExitDateTime(exitTime);
                         parkingTransaction.setDuration(Integer.valueOf(String.valueOf(diffMinutes)));
                         parkingTransaction.setPaymentAmount((long)Double.parseDouble(CSVdtoList.get(i).getPaidAmt())*100L);
                         parkingTransaction.setTranAmount((long)Double.parseDouble(CSVdtoList.get(i).getPaidAmt())*100L);
                         parkingTransaction.setVehicleType(CSVdtoList.get(i).getType());
                         parkingTransaction.setCarPlateNo(CSVdtoList.get(i).getVehicleNo());
                         parkingTransaction.setEntrySessionId(CSVdtoList.get(i).getIuNo()+parkingTransaction.getEntryDateTime().format(formatter2));
                         parkingTransaction.setExitSessionId(CSVdtoList.get(i).getIuNo()+parkingTransaction.getExitDateTime().format(formatter2));
                         parkingTransaction.setCarParkId(1500L);
                         String carPlateNo = CSVdtoList.get(i).getVehicleNo();
                         parkingTransaction.setCarPlateNo(StringUtils.isEmpty(carPlateNo) ? "No Car Plate" : carPlateNo);
                         parkingTransaction.setCreatedDate(ZonedDateTime.now());
                         parkingTransaction.setCreatedBy("csvadmin");
                         parkingTransaction.setLastModifiedDate(ZonedDateTime.now());
                         parkingTransaction.setLastModifiedBy("csvadmin");

                         log.debug("Before saving to DB : " + parkingTransaction);
                         //TODO check for duplicates, if yes then update
                         List<ParkingTransaction> parkingTransactionList = new ArrayList<ParkingTransaction>();
                         parkingTransactionList = parkingTransactionRepository.findByEntrySessionIdAndExitSessionId(parkingTransaction.getEntrySessionId(),parkingTransaction.getExitSessionId());
                         if(parkingTransactionList.size()>0){
                             log.debug("parkingTransactionList : " + parkingTransactionList);
                             parkingTransaction.setId(parkingTransactionList.get(0).getId());
                             parkingTransaction.setId(parkingTransactionList.get(0).getId());
                             parkingTransaction.setCreatedBy(parkingTransactionList.get(0).getCreatedBy());
                             parkingTransaction.setCreatedDate(parkingTransactionList.get(0).getCreatedDate());
                         }
                         parkingTransactionRepository.save(parkingTransaction);
                     }
                 }
            }

        } catch (Exception e) {
            log.debug("Error in data");
            e.printStackTrace();
            e.getMessage();
            return false;
            }

        return true;
    }
}
