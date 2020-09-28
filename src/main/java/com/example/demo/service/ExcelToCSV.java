package com.example.demo.service;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToCSV {

    final Logger log = Logger.getLogger(ExcelToCSV.class.getName());

    public void readExcel(String inputFilePath, String outFilePath) throws IOException {

        String extType = StringUtils.substringAfterLast(inputFilePath,".");
        log.debug("" + extType);

        FileInputStream fis = new FileInputStream(inputFilePath);

        //required to print xls, xlsx to csv
        DataFormatter formatter = new DataFormatter();
        //print to csv
        PrintStream out = new PrintStream(new FileOutputStream(outFilePath),true, "UTF-8");

        //xls format
        if("xls".equals(extType)) {
            try {
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0);
            FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
            for (Row row : sheet) {
                if(row.getRowNum()==0 || row.getRowNum()==1){
                    continue;
                }
                for (Cell cell : row) {
                    switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
                        case NUMERIC:
//                            System.out.print(cell.getNumericCellValue() + "\t\t");
                            out.print(formatter.formatCellValue(cell));
                            break;
                        case STRING:
//                            System.out.print(cell.getStringCellValue() + "\t\t");
                            out.print(formatter.formatCellValue(cell));
                            break;
                    }
                    out.print(",");
                }
                out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        //xlsx format
        if("xlsx".equals(extType)) {
            try {
                XSSFWorkbook wbx = new XSSFWorkbook(fis);
                XSSFSheet sheetx = wbx.getSheetAt(0);
                Iterator<Row> rowIterator = sheetx.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if(row.getRowNum()==0 || row.getRowNum()==1 || row.getRowNum()==2 || row.getRowNum()==3){
                        continue;
                    }
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getCellType()) {
                            case STRING:
//                                System.out.print(cell.getStringCellValue() + "\t\t\t");
                                out.print(formatter.formatCellValue(cell));
                                break;
                            case NUMERIC:
//                                System.out.print(cell.getNumericCellValue() + "\t\t\t");
                                out.print(formatter.formatCellValue(cell));
                                break;
                            default:
                                log.debug("Error in data");
                        }
                        out.print(",");
                    }
                    out.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //csv format
        if("csv".equals(extType)) {
            try {
                Scanner sc = new Scanner(new File(inputFilePath));
                sc.useDelimiter(",");
                while (sc.hasNext()) {
                    out.print(sc.next());
                    out.print(',');
                }
                sc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
