package com.example.demo;

import com.example.demo.service.ExcelToCSVService;
import com.example.demo.service.impl.ExcelToCSV;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class ExcelreaderApplicationTests {

	ExcelToCSVService etcs;
	//		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\W7_Movement Report.xls";
//	String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\TW2.xlsx";
			String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\TW2 - Copy.xl";
//		String inputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\MoveTrans_10_Raeburn_Park_01082020_31082020_15092020 for ST.csv";
	String date = DateTimeFormatter.ofPattern("ddMMyyyyhhmmss").format(ZonedDateTime.now());
	String outputFileName = "report" + date + ".csv";
	String outputFilePath = "C:\\Users\\fiona\\Desktop\\ST\\dashboard\\" + outputFileName;

//	@Test
//	void contextLoads() {
//	}

//	@Test
//	void readXLStoCSV() throws Exception {
//		assertTrue(etc.readExcel(inputFilePath,outputFilePath));
//	}
//
//	@Test
//	void failXLStoCSV() throws Exception {
//		assertFalse(etc.readExcel(inputFilePath,outputFilePath));
//	}

}
