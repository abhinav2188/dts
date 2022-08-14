package com.example.art.services;

import com.example.art.dto.request.DealQueryRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.model.views.BrochureUrlView;
import com.example.art.repository.BrochureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private BrochureRepository brochureRepository;

    @Value("${spring.mail.username}") private String sender;

    public BaseResponse sendDealQueryMail(Long dealId, DealQueryRequest details){

        // todo : check auth

        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipients().split(","));
            mailMessage.setText(getEmailBody(details));
            mailMessage.setSubject("ART Query Details");
            javaMailSender.send(mailMessage);

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

}
