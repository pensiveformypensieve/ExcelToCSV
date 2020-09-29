package com.example.demo.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class ExcelToCSV {

    final Logger log = Logger.getLogger(ExcelToCSV.class.getName());

    public Boolean readExcel(String inputFilePath, String outFilePath) throws Exception {

//        String extType = StringUtils.substringAfterLast(inputFilePath,".");
        String extType = FilenameUtils.getExtension(inputFilePath);
        log.debug("" + extType);

        FileInputStream fis = new FileInputStream(inputFilePath);

        //required to print xls, xlsx to csv
        DataFormatter formatter = new DataFormatter();
        //print to csv
        PrintStream out = new PrintStream(new FileOutputStream(outFilePath), true, "UTF-8");

        try{

            if (!extType.equals("xls") && !extType.equals("xlsx") && !extType.equals("csv")) {
            throw new Exception("Invalid data type");
            }

            //xls format
            if ("xls".equals(extType)) {
                HSSFWorkbook wb = new HSSFWorkbook(fis);
                HSSFSheet sheet = wb.getSheetAt(0);
                FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
                for (Row row : sheet) {
                    //to skip headers
                    if (row.getRowNum()<2) {
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

            }

            //xlsx format
            if ("xlsx".equals(extType)) {
                XSSFWorkbook wbx = new XSSFWorkbook(fis);
                XSSFSheet sheetx = wbx.getSheetAt(0);
//                Iterator<Row> rowIterator = sheetx.iterator();
//                while (rowIterator.hasNext()) {
//                    Row row = rowIterator.next();
                for(Row row : sheetx){
                    //to skip headers
                    if (row.getRowNum()<7) {
                        continue;
                    }
                    for(int cn=0; cn<row.getLastCellNum(); cn++){
//                    Iterator<Cell> cellIterator = row.cellIterator();
//                    while (cellIterator.hasNext()) {
//                        Cell cell = cellIterator.next();

                        //to enable blank cells to be printed in switch case
                        Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        switch (cell.getCellType()) {
                            case STRING:
//                                System.out.print(cell.getStringCellValue() + "\t\t\t");
                                out.print(formatter.formatCellValue(cell));
                                break;
                            case NUMERIC:
//                                System.out.print(cell.getNumericCellValue() + "\t\t\t");
                                out.print(formatter.formatCellValue(cell));
                                break;
                            case BLANK:
//                                System.out.print(cell.getNumericCellValue() + "\t\t\t");
                                out.print(formatter.formatCellValue(cell));
                                break;
                            default:
                                out.print(formatter.formatCellValue(cell));
                        }
//                    }
//                    }
                    out.print(",");
                    }

                out.println();
                }
            }

            //csv format
             if ("csv".equals(extType)) {
                Scanner sc = new Scanner(new File(inputFilePath));
                sc.useDelimiter(",");
                //to skip headers
                sc.nextLine();
                while (sc.hasNext()) {
                    out.print(sc.next());
                    out.print(',');
                }
                sc.close();
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
