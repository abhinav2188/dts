package com.example.art.services.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.example.art.model.views.DealExcelView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Component
public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public void listToExcel(List<DealExcelView> objects, Class<?> clazz, String sheetName, HttpServletResponse response) {

        try {

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());

            // header row
            Row headerRow = sheet.createRow(0);
            int col = 0;
            for(Field field : fields){
                Cell cell = headerRow.createCell(col++);
                field.setAccessible(true);
                cell.setCellValue(field.getName());
                field.setAccessible(false);
            }

            int rowIdx = 1;
            for(Object object : objects){
                Row row = sheet.createRow(rowIdx++);
                col = 0;
                for(Field field : fields){
                    Cell cell = row.createCell(col++);
                    field.setAccessible(true);
                    cell.setCellValue(String.valueOf(field.get(object)));
                    field.setAccessible(false);
                }
            }
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            workbook.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("error while reading values from object using reflect: "+e.getMessage());
        }
    }

    public <T>void listToSheet(Workbook workbook, String sheetName, List<T> objects) {

        if(objects.size() == 0) return;
        Class<?> clazz = objects.get(0).getClass();

        try {

            Sheet sheet = workbook.createSheet(sheetName);
            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());

            // header row
            Row headerRow = sheet.createRow(0);
            int col = 0;
            for(Field field : fields){
                Cell cell = headerRow.createCell(col++);
                field.setAccessible(true);
                cell.setCellValue(field.getName());
                field.setAccessible(false);
            }

            // data rows
            int rowIdx = 1;
            for(Object object : objects){
                Row row = sheet.createRow(rowIdx++);
                col = 0;
                for(Field field : fields){
                    Cell cell = row.createCell(col++);
                    field.setAccessible(true);
                    cell.setCellValue(String.valueOf(field.get(object)));
                    field.setAccessible(false);
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("error while reading values from object using reflect: "+e.getMessage());
        }
    }

}