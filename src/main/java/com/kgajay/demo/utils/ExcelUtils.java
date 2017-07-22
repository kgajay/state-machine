package com.kgajay.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author ajay.kg created on 22/07/17.
 */
@Slf4j
public class ExcelUtils {

    private XSSFWorkbook workbook;
    private String excelFilePath;

    public ExcelUtils(String excelFilePath) {
        workbook = new XSSFWorkbook();
        this.excelFilePath = excelFilePath;
        File f = new File(this.excelFilePath);
        if (f.exists()) {
            f.delete();
        }
    }

    public XSSFSheet createSheet() {
        return workbook.createSheet();
    }

    public void writeToExcel(List<String> data, XSSFSheet sheet, int rowCount) {
        Row row = sheet.createRow(rowCount);
        int len = data.size();
        for (int i = 0 ; i < len; i++) {
            row.createCell(i).setCellValue(data.get(i));
        }
    }

    public void flushToFile() throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }
}
