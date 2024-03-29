package com.example.art.services.impl;

import com.example.art.dto.mapper.DealHistoryMapper;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.DealHistoryResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.Deal;
import com.example.art.model.DealHistory;
import com.example.art.model.enums.DealSubject;
import com.example.art.repository.DealHistoryRepository;
import com.example.art.services.DealHistoryService;
import com.example.art.services.helper.ServiceUtils;
import com.example.art.utils.Constants;
import com.example.art.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private DealHistoryMapper dealHistoryMapper;

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
                    dealHistory.setCreatedAt(current);
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

    @Override
    public BaseResponse<DealHistoryResponse> getDealHistory(Long dealId, int pageNo, int pageSize) throws NoAuthorizationException, EntityNotFoundException {
        Deal deal = serviceUtils.getDeal(dealId);
        serviceUtils.checkUserAuthorization(deal);
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());

        Page<DealHistory> page = dealHistoryRepository.findByDealId(dealId, pageable);

        DealHistoryResponse historyResponse = dealHistoryMapper.getDealHistoryResponse(page);

        return BaseResponse.<DealHistoryResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg("fetched deal history logs")
                .data(historyResponse)
                .build();
    }

}
