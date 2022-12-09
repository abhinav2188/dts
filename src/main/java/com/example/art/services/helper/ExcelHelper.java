package com.example.art.services.helper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.example.art.model.views.DealExcelView;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static CellStyle DATE_CELL_STYLE;

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

        // Date cell style
        CellStyle dateCellStyle = createDateCellStyle(workbook);

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
                log.debug("data object: {}",object);
                Row row = sheet.createRow(rowIdx++);
                col = 0;
                for(Field field : fields){
                    Cell cell = row.createCell(col++);
                    field.setAccessible(true);
                    Object val = field.get(object);
                    if(val == null){
                        // do nothing
                    }else if(val instanceof Date){
                        cell.setCellStyle(dateCellStyle);
                        cell.setCellValue((Date) val);
                    }else if(val instanceof Number){
                        cell.setCellValue(Double.parseDouble(String.valueOf(val)));
                    }else if(val instanceof Boolean){
                        cell.setCellValue((boolean)val);
                    }else{
                        cell.setCellValue(String.valueOf(val));
                    }
                    field.setAccessible(false);
                }
                log.debug("row created: {}", row);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("error while reading values from object using reflect: "+e.getMessage());
        }
    }

    private CellStyle createDateCellStyle(Workbook workbook) {
        CreationHelper createHelper =workbook.getCreationHelper();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy hh:mm"));
        return dateCellStyle;
    }

}