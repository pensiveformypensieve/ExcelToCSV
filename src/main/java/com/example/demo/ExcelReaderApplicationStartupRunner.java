package com.example.demo;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demo.service.ExcelToCSVService;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ExcelReaderApplicationStartupRunner implements CommandLineRunner {

    final static Logger log = Logger.getLogger(ExcelReaderApplicationStartupRunner.class.getName());

    private final ExcelToCSVService excelToCSVService;

    public ExcelReaderApplicationStartupRunner(ExcelToCSVService excelToCSVService) {
        this.excelToCSVService = excelToCSVService;
    }

    @Override
    public void run (String...args) throws Exception{

        		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\W7_Movement Report.xls";
//		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\TW2.xlsx";
////		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\TW2 - Copy.xl";
//		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\MoveTrans_10_Raeburn_Park_01082020_31082020_15092020 for ST.csv";

		String date = DateTimeFormatter.ofPattern("ddMMyyyyhhmmss").format(ZonedDateTime.now());
		//to not reuse the same file name and get error when file is still opened
		String outputFileName = "report" + date + ".csv";
		String outputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\" + outputFileName;
		log.debug(""+outputFilePath);
//
		excelToCSVService.readExcel(inputFilePath, outputFilePath);
    }
}
