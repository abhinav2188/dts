package com.example.art.services;

import com.example.art.dto.request.DealQueryRequest;
import com.example.art.dto.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public BaseResponse sendSimpleMail(DealQueryRequest details){
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
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

}
