package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface ExcelToCSVService {

    Boolean readExcel(String inputFilePath, String outputFilePath) throws Exception;

}
