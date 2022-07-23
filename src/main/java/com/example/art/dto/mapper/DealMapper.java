package com.example.art.dto.mapper;

import com.example.art.dto.CreateDealResponse;
import com.example.art.dto.request.CreateDealRequest;
import com.example.art.model.Deal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DealMapper {

    public CreateDealResponse getCreateDealResponse(Deal deal){
        CreateDealResponse response = new CreateDealResponse();
        response.setDealId(deal.getId());
        response.setDealName(deal.getName());
        return response;
    }

    public Deal getDeal(CreateDealRequest request){
        Deal deal = new Deal();
        deal.setName(request.getDealName());
        deal.setCoOwners(new ArrayList<>());
        return deal;
    }

}
