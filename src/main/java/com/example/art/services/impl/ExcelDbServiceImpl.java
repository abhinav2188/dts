package com.example.art.services.impl;

import com.example.art.model.views.DealExcelView;
import com.example.art.repository.DealRepository;
import com.example.art.services.ExcelDbService;
import com.example.art.services.helper.ExcelHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ExcelDbServiceImpl implements ExcelDbService {

    @Autowired
    private ExcelHelper excelHelper;

    @Autowired
    private DealRepository dealRepository;

    @Override
    public void getDbExcel(HttpServletResponse response) throws IOException {

        List<DealExcelView> viewList = dealRepository.findAllDealExcelView_Named();
        log.info("DealExcelView list: {}",viewList);

        Workbook workbook = new XSSFWorkbook();

        excelHelper.<DealExcelView>listToSheet(workbook,"Deals", viewList);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();

    }
}
