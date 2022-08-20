package com.example.art.controllers.internal;

import com.example.art.services.ExcelDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/api/int/download/db")
public class ExcelDbController {

    @Autowired
    private ExcelDbService excelDbService;

    @GetMapping
    public void getDbExcel(HttpServletResponse response) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
        String currentDateTime = dateFormat.format(new Date());
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=deals_"+currentDateTime+".xlsx";
        response.setHeader(headerKey,headerValue);
        excelDbService.getDbExcel(response);
    }
}
