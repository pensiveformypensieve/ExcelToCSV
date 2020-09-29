package com.example.demo;

import com.example.demo.service.ExcelToCSV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.DateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@SpringBootApplication
public class ExcelreaderApplication {

	final static Logger log = Logger.getLogger(ExcelreaderApplication.class.getName());


	public static void main(String[] args) throws Exception {
		SpringApplication.run(ExcelreaderApplication.class, args);

//		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\W7_Movement Report.xls";
		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\TW2.xlsx";
//		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\TW2 - Copy.xl";
//		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\MoveTrans_10_Raeburn_Park_01082020_31082020_15092020 for ST.csv";

		String date = DateTimeFormatter.ofPattern("ddMMyyyyhhmmss").format(ZonedDateTime.now());
		//to not reuse the same file name and get error when file is still opened
		String outputFileName = "report" + date + ".csv";
		String outputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\" + outputFileName;
		log.debug(""+outputFilePath);

		ExcelToCSV etc = new ExcelToCSV();
		etc.readExcel(inputFilePath, outputFilePath);
	}

}
