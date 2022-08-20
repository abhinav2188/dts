package com.example.art.services;

import com.example.art.dto.request.DealQueryRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.Deal;
import com.example.art.model.enums.DealSubject;
import com.example.art.model.views.BrochureUrlView;
import com.example.art.repository.BrochureRepository;
import com.example.art.services.helper.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    // todo: make different email service for generic use

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private BrochureRepository brochureRepository;

    @Value("${spring.mail.username}") private String sender;

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private DealHistoryService dealHistoryService;

    public BaseResponse sendDealQueryMail(Long dealId, DealQueryRequest details) throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = serviceUtils.getDeal(dealId);
        serviceUtils.checkUserAuthorization(deal);

        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String body = getEmailBody(details);
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipients().split(","));
            mailMessage.setText(body);
            mailMessage.setSubject("ART Query Details");
            javaMailSender.send(mailMessage);

            dealHistoryService.addDealHistory(dealId, DealSubject.SENT_DEAL_QUERY,
                    new DealQueryHistory(details.getRecipients(), body));

            return BaseResponse.builder()
                    .status(HttpStatus.OK)
                    .responseMsg("mail sent successfully")
                    .build();
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    private String getEmailBody(DealQueryRequest details) {
        StringBuffer body = new StringBuffer("Links for the brochures for details :\n\n");
        List<String> brochureNames = Arrays.asList(details.getBrochures().split(","));
        List<BrochureUrlView> brochureUrls = brochureRepository.findByBrochureNameIn(brochureNames);
        for(BrochureUrlView brochureUrl : brochureUrls){
            body.append(brochureUrl.getBrochureName()+": "+brochureUrl.getFilePath()+"\n");
        }
        return body.toString();
    }

    @AllArgsConstructor
    @Data
    private class DealQueryHistory{
        private String recipients;
        private String details;
    }

}
