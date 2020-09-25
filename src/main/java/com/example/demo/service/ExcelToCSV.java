package com.example.demo.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToCSV {

    final Logger log = Logger.getLogger(ExcelToCSV.class.getName());

    public String readExcel(String inputFilePath, String outFilePath) throws IOException {

        String extType = StringUtils.substringAfterLast(inputFilePath,".");
        log.debug("" + extType);

        FileInputStream fis = new FileInputStream(inputFilePath);

        //xls format
        if("xls".equals(extType)) {
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0);
            FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t\t");
                            break;
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t\t");
                            break;
                    }
                }
                System.out.println();
            }
        }

        //xlsx format
        if("xlsx".equals(extType)) {
            try {
                XSSFWorkbook wbx = new XSSFWorkbook(fis);
                XSSFSheet sheetx = wbx.getSheetAt(0);     //creating a Sheet object to retrieve object
                Iterator<Row> itr = sheetx.iterator();    //iterating over excel file
                while (itr.hasNext()) {
                    Row row = itr.next();
                    Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getCellType()) {
                            case STRING:    //field that represents string cell type
                                System.out.print(cell.getStringCellValue() + "\t\t\t");
                                break;
                            case NUMERIC:    //field that represents number cell type
                                System.out.print(cell.getNumericCellValue() + "\t\t\t");
                                break;
                            default:
                        }
                    }
                    System.out.println("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
