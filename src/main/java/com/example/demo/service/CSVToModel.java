package com.example.demo.service;

import com.example.demo.domain.ParkingTransaction;
import com.example.demo.repository.ParkingTransactionRepository;
import com.example.demo.service.dto.XLSdto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//XLS -> CSV -> ParkingTransaction
public class CSVToModel {

    final Logger log = Logger.getLogger(CSVToModel.class.getName());

    public Boolean readXLSCSV(Integer fileType, String inputFilePath) throws Exception {

        String extType = FilenameUtils.getExtension(inputFilePath);
        log.debug("" + inputFilePath);
        log.debug("" + extType);

        FileInputStream fis = new FileInputStream(inputFilePath);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("ddMMyyyyhhmmss");
        
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
                     List<ParkingTransaction> parkingTransactions = new ArrayList<ParkingTransaction>();

                     for (int i = 0; i < XLSdtoList.size(); i++) {
                         log.debug("saving to db");
                        ParkingTransaction parkingTransaction = new ParkingTransaction();

                         //TODO set values to parkingTransaction then save to table
                         log.debug("entry time: "+XLSdtoList.get(i).getEntryTime());
                         log.debug("exit time: "+XLSdtoList.get(i).getExitTime());
                         log.debug("zoned date time: "+ZonedDateTime.now());
                         String entry = XLSdtoList.get(i).getEntryTime();
                         String modifiedEntryTime = entry.replace ( " " , "T" );
                         ZonedDateTime entryTime = LocalDateTime.parse(modifiedEntryTime, formatter).atZone(ZoneId.systemDefault());
                         log.debug(""+entryTime);
                        parkingTransaction.setEntryDateTime(entryTime);
                         String exit = XLSdtoList.get(i).getExitTime();
                         String modifiedExitTime = entry.replace ( " " , "T" );
                         ZonedDateTime exitTime = LocalDateTime.parse (modifiedExitTime , formatter).atZone(ZoneId.systemDefault());
                        parkingTransaction.setExitDateTime(exitTime);
                        parkingTransaction.setDuration(Integer.parseInt(XLSdtoList.get(i).getParkedTime()));
                        parkingTransaction.setPaymentAmount(Long.parseLong(XLSdtoList.get(i).getPaid())*100L);
                        parkingTransaction.setTranAmount(Long.parseLong(XLSdtoList.get(i).getPaid())*100L);
                        parkingTransaction.setVehicleType(XLSdtoList.get(i).getParkingType());
                        parkingTransaction.setCarPlateNo(XLSdtoList.get(i).getVehicleNo());
                        parkingTransaction.setEntrySessionId(XLSdtoList.get(i).getIuNo()+parkingTransaction.getEntryDateTime().format(formatter2));
                        parkingTransaction.setExitSessionId(XLSdtoList.get(i).getIuNo()+parkingTransaction.getExitDateTime().format(formatter2));
                        parkingTransaction.setCarParkId(1500L);



                        parkingTransactions.add(parkingTransaction);
                     }
                     log.debug("parkingTransactions :" + parkingTransactions);
                 }

//                 if(fileType == 2) {}
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
