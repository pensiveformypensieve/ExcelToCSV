package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface CSVToModelService {

    Boolean readXLSCSV(Integer fileType, String inputFilePath) throws Exception;

}
