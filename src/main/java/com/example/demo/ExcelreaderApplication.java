package com.example.demo;

import com.example.demo.service.ExcelToCSV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.Logger;

import java.io.IOException;


@SpringBootApplication
public class ExcelreaderApplication {

	final static Logger log = Logger.getLogger(ExcelreaderApplication.class.getName());


	public static void main(String[] args) throws IOException {
		SpringApplication.run(ExcelreaderApplication.class, args);

//		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\W7_Movement Report.xls";
		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\TW2.xlsx";
//		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\MoveTrans_10_Raeburn_Park_01082020_31082020_15092020 for ST.csv";


		String outFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\new.txt";

		ExcelToCSV etc = new ExcelToCSV();
		etc.readExcel(inputFilePath, outFilePath);
	}

}
