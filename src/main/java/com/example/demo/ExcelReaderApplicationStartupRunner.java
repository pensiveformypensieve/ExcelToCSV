package com.example.demo;

import com.example.demo.service.ExcelToCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ExcelReaderApplicationStartupRunner implements CommandLineRunner {

    final static Logger log = Logger.getLogger(ExcelReaderApplicationStartupRunner.class.getName());

    private final ExcelToCSVService excelToCSVService;

    public ExcelReaderApplicationStartupRunner(ExcelToCSVService excelToCSVService) {
        this.excelToCSVService = excelToCSVService;
    }

    @Override
    public void run (String...args) throws Exception{

        		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\W7_Movement Report_test.xls";
////		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\report29092020084354.csv";
////		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\TW2.xlsx";
////		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\TW2 - Copy.xl";
////		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\MoveTrans_10_Raeburn_Park_01082020_31082020_15092020 for ST.csv";
//
		String date = DateTimeFormatter.ofPattern("ddMMyyyyhhmmss").format(ZonedDateTime.now());
		//to not reuse the same file name and get error when file is still opened
		String outputFileName = "report" + date + ".csv";
		String outputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\" + outputFileName;
		log.debug(""+outputFilePath);
//
		excelToCSVService.readExcel(inputFilePath, outputFilePath);

//		CSVToModel test = new CSVToModel();
//		test.readXLSCSV(1,inputFilePath);

    }
}
