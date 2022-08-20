package com.example.art.services.impl;

import com.example.art.model.views.*;
import com.example.art.repository.*;
import com.example.art.services.ExcelDbService;
import com.example.art.services.helper.ExcelHelper;
import com.example.art.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExcelDbServiceImpl implements ExcelDbService {

    @Autowired
    private ExcelHelper excelHelper;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private MapperUtils mapperUtils;

    @Override
    public void getDbExcel(HttpServletResponse response) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        List<UserExcelView> userExcelViews = userRepository.findByIdNotNull();
        excelHelper.<UserExcelView>listToSheet(workbook,"Users", userExcelViews);

        List<PartyExcelView> partyExcelViews = partyRepository.findByIdNotNull();
        excelHelper.<PartyExcelView>listToSheet(workbook,"Party", partyExcelViews);

        List<DealExcelView> viewList = dealRepository.findAllDealExcelView_Named();
        List<DealExcelViewPartial> partials = dealRepository.findByIdNotNull();
        updateDealExcelView(viewList,partials);
        excelHelper.<DealExcelView>listToSheet(workbook,"Deals", viewList);

        List<InteractionExcelView> interactionExcelViews = interactionRepository.findAllInteractionExcelViews_Named();
        excelHelper.<InteractionExcelView>listToSheet(workbook,"Interactions", interactionExcelViews);

        List<ContactExcelView> contactExcelViews = contactRepository.findAllContactExcelViews_Named();
        excelHelper.<ContactExcelView>listToSheet(workbook,"Contacts", contactExcelViews);

        List<ConsultantExcelView> consultantExcelViews = consultantRepository.findAllConsultantExcelViews_Named();
        excelHelper.<ConsultantExcelView>listToSheet(workbook,"Consultants", consultantExcelViews);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();

    }

    private void updateDealExcelView(List<DealExcelView> viewList, List<DealExcelViewPartial> partials) {
        for(DealExcelView view : viewList){
            Optional<DealExcelViewPartial> partial = partials.stream().filter((p) -> {
                return view.getId() == p.getId();
            }).findAny();
            if(partial.isPresent()){
                mapperUtils.updateEntity(view, partial.get(), new ArrayList<>());
            }
        }
    }
}
