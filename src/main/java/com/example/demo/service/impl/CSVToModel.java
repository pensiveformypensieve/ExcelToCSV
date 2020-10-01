package com.example.demo.service.impl;

import com.example.demo.domain.ParkingTransaction;
import com.example.demo.repository.ParkingTransactionRepository;
import com.example.demo.service.CSVToModelService;
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
                .appendPattern("dd/MM HH:mm:ss")
                .parseDefaulting(ChronoField.YEAR, 2020)
                .toFormatter();
        DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("H[H]:mm");
        DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("mm");

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
                         ZonedDateTime entryTime = ZonedDateTime.parse(entry, formatter.withZone(ZoneId.systemDefault()));
                         log.debug("converted entry time : "+entryTime);
                        parkingTransaction.setEntryDateTime(entryTime);
                         String exit = XLSdtoList.get(i).getExitTime();
                         log.debug("exit time: "+XLSdtoList.get(i).getExitTime());
                         ZonedDateTime exitTime = ZonedDateTime.parse (exit, formatter.withZone(ZoneId.systemDefault()));
                         log.debug("converted exit time : "+exitTime);
                        parkingTransaction.setExitDateTime(exitTime);
                        parkingTransaction.setDuration(Integer.parseInt(XLSdtoList.get(i).getParkedTime()));
                        parkingTransaction.setPaymentAmount(Long.parseLong(XLSdtoList.get(i).getPaid())*100L);
                        parkingTransaction.setTranAmount(Long.parseLong(XLSdtoList.get(i).getPaid())*100L);
                        parkingTransaction.setVehicleType(XLSdtoList.get(i).getParkingType());
                        parkingTransaction.setEntrySessionId(XLSdtoList.get(i).getIuNo()+parkingTransaction.getEntryDateTime().format(formatter2));
                        parkingTransaction.setExitSessionId(XLSdtoList.get(i).getIuNo()+parkingTransaction.getExitDateTime().format(formatter2));
                        parkingTransaction.setCarParkId(1500L);
                        String carPlateNo = XLSdtoList.get(i).getVehicleNo();
                        parkingTransaction.setCarPlateNo(StringUtils.isEmpty(carPlateNo) ? "No Car Plate" : carPlateNo);
                        parkingTransaction.setCreatedDate(ZonedDateTime.now());
                        parkingTransaction.setCreatedBy("admin");
                        parkingTransaction.setLastModifiedDate(ZonedDateTime.now());
                        parkingTransaction.setLastModifiedBy("admin");

                        log.debug("Before saving to DB : " + parkingTransaction);
                        //TODO check for duplicates, if yes then update
                        List<ParkingTransaction> parkingTransactionList = new ArrayList<ParkingTransaction>();
                        parkingTransactionList = parkingTransactionRepository.findByEntrySessionIdAndExitSessionId(parkingTransaction.getEntrySessionId(),parkingTransaction.getExitSessionId());
                        if(parkingTransactionList.size()>0){
                            log.debug("parkingTransactionList : " + parkingTransactionList);
                            parkingTransaction.setId(parkingTransactionList.get(0).getId());
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
//                     List<ParkingTransaction> parkingTransactions = new ArrayList<ParkingTransaction>();

                     for (int i = 0; i < XLSXdtoList.size(); i++) {
                         log.debug("saving to db");
                        ParkingTransaction parkingTransaction = new ParkingTransaction();

                         String entry = XLSXdtoList.get(i).getEntryTime();
                         log.debug("entry time: "+XLSXdtoList.get(i).getEntryTime());
                         ZonedDateTime entryTime = ZonedDateTime.parse(entry, formatter3.withZone(ZoneId.systemDefault()));
                         log.debug("converted entry time : "+entryTime);
                        parkingTransaction.setEntryDateTime(entryTime);
                         String exit = XLSXdtoList.get(i).getExitTime();
                         log.debug("exit time: "+XLSXdtoList.get(i).getExitTime());
                         ZonedDateTime exitTime = ZonedDateTime.parse(exit, formatter3.withZone(ZoneId.systemDefault()));
                         log.debug("converted exit time : "+exitTime);
                        parkingTransaction.setExitDateTime(exitTime);
                        String parkedTime = XLSXdtoList.get(i).getParkedTime();
                        log.debug("parkedTime : "+parkedTime);
                        log.debug("parkedTime : " + LocalTime.parse(parkedTime,formatter4));
                        LocalTime parkedDuration = LocalTime.parse(parkedTime,formatter4);
                        log.debug("parkedDuration : " + (parkedDuration.getHour()*60+parkedDuration.getMinute()));
                        parkingTransaction.setDuration((parkedDuration.getHour()*60+parkedDuration.getMinute()));
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
                        parkingTransaction.setCreatedBy("admin");
                        parkingTransaction.setLastModifiedDate(ZonedDateTime.now());
                        parkingTransaction.setLastModifiedBy("admin");

                        log.debug("Before saving to DB : " + parkingTransaction);
                        //TODO check for duplicates, if yes then update
                        List<ParkingTransaction> parkingTransactionList = new ArrayList<ParkingTransaction>();
                        parkingTransactionList = parkingTransactionRepository.findByEntrySessionIdAndExitSessionId(parkingTransaction.getEntrySessionId(),parkingTransaction.getExitSessionId());
                        if(parkingTransactionList.size()>0){
                            log.debug("parkingTransactionList : " + parkingTransactionList);
                            parkingTransaction.setId(parkingTransactionList.get(0).getId());
                        }
                        parkingTransactionRepository.save(parkingTransaction);

//                        parkingTransactions.add(parkingTransaction);
                     }

//                 log.debug("parkingTransactions :" + parkingTransactions);
                 }

                 //CSV format
//                 if(fileType == 3) {}
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
