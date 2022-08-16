package com.example.art.services.impl;

import com.example.art.model.DealHistory;
import com.example.art.model.enums.DealSubject;
import com.example.art.repository.DealHistoryRepository;
import com.example.art.services.DealHistoryService;
import com.example.art.utils.Constants;
import com.example.art.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DealHistoryServiceImpl implements DealHistoryService {

    @Autowired
    private DealHistoryRepository dealHistoryRepository;

    @Override
    public void addDealHistory(Long dealId, DealSubject subject, Object data){

        Long userId = Long.parseLong(MDC.get(Constants.USER_ID));
        String userEmail = MDC.get(Constants.USER_EMAIL);
        Date current= new Date();

        log.info("in addDealHistory");
        try{
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    log.info("started async execution");
                    String details = null;
                    try {
                        details = JsonUtils.convertObjectToJsonString(data);
                        log.info(details);
                    } catch (JsonProcessingException e) {
                        log.info("error parsing deal history details, {}",e.getMessage());
                    }
                    DealHistory dealHistory = new DealHistory();
                    dealHistory.setCreatedAt(current.toString());
                    dealHistory.setDealId(dealId);
                    dealHistory.setUserId(userId);
                    dealHistory.setUserEmail(userEmail);
                    dealHistory.setTitle(subject.name());
                    dealHistory.setDetails(details);
                    dealHistoryRepository.save(dealHistory);
                    log.info("history saved");
                }
            }, new ThreadPoolExecutor(40, 50, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000)));
        }catch (Exception e){
            log.info("error in publishing deal history, {}",e.getMessage());
        }
    }
}
