package com.baizhi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class testPio {
    @Test
    public void testPioOut() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell0 = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);
        HSSFCell cell3 = row.createCell(3);
        cell0.setCellValue("ID");
        cell1.setCellValue("Name");
        cell2.setCellValue("Password");
        cell3.setCellValue("Bir");
        try {
            workbook.write(new FileOutputStream(new File("C:/Users/Lenovo/Desktop/111/user.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
