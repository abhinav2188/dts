package com.example.art.dto.mapper;

import com.example.art.dto.CreateDealResponse;
import com.example.art.dto.request.CreateDealRequest;
import com.example.art.dto.request.UpdateDealSection3Request;
import com.example.art.dto.request.UpdateDealSection4Request;
import com.example.art.dto.request.UpdateProductDetailsRequest;
import com.example.art.model.Deal;
import com.example.art.utils.MapperUtils;
import com.example.art.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DealMapper {

    @Autowired
    private StringUtils stringUtils;

    @Autowired
    private MapperUtils mapperUtils;

    public CreateDealResponse getCreateDealResponse(Deal deal){
        CreateDealResponse response = new CreateDealResponse();
        response.setDealId(deal.getId());
        response.setDealName(deal.getName());
        return response;
    }

    public Deal getDeal(CreateDealRequest request){
        Deal deal = new Deal();
        deal.setIsActive(true);
        deal.setName(request.getDealName());
        deal.setCoOwners(new ArrayList<>());
        return deal;
    }

    public int updateProductDetails(Deal deal, UpdateProductDetailsRequest requestDto) {
        List<String> updateMsgs = new ArrayList<>();
        int count = mapperUtils.updateEntity(deal,requestDto,updateMsgs);
        log.info("Deal with id {} updated : {}",deal.getId(), updateMsgs);
        return count;
    }

    public int updateDeal(Deal deal, UpdateDealSection3Request requestDto) {
        List<String> updateMsgs = new ArrayList<>();
        int count = mapperUtils.updateEntity(deal,requestDto,updateMsgs);
        log.info("Deal with id {} updated : {}",deal.getId(), updateMsgs);
        return count;
    }

    public int updateDeal(Deal deal, UpdateDealSection4Request requestDto) {
        List<String> updateMsgs = new ArrayList<>();
        int count = mapperUtils.updateEntity(deal,requestDto,updateMsgs);
        log.info("Deal with id {} updated : {}",deal.getId(), updateMsgs);
        return count;
    }

}
