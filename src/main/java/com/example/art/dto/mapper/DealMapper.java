package com.example.art.dto.mapper;

import com.example.art.dto.CreateDealResponse;
import com.example.art.dto.request.CreateDealRequest;
import com.example.art.dto.request.UpdateDealSection3Request;
import com.example.art.dto.request.UpdateDealSection4Request;
import com.example.art.dto.request.UpdateProductDetailsRequest;
import com.example.art.dto.response.DealDetailResponse;
import com.example.art.dto.response.DealDetailResponse2;
import com.example.art.dto.response.MultipleDealsResponse;
import com.example.art.dto.response.inner.*;
import com.example.art.model.Deal;
import com.example.art.model.User;
import com.example.art.utils.MapperUtils;
import com.example.art.utils.MessageUtils;
import com.example.art.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
        deal.setOpeningDate(new Date());
        return deal;
    }

    public Map<String,String> updateProductDetails(Deal deal, UpdateProductDetailsRequest requestDto) {
        Map<String,String> updatedAttibutes = mapperUtils.updateEntity(deal,requestDto);
        return updatedAttibutes;
    }

    public Map<String,String> updateDeal(Deal deal, UpdateDealSection3Request requestDto) {
        Map<String,String> updatedAttibutes = mapperUtils.updateEntity(deal,requestDto);
        return updatedAttibutes;
    }

    public Map<String,String> updateDeal(Deal deal, UpdateDealSection4Request requestDto) {
        Map<String,String> updatedAttibutes = mapperUtils.updateEntity(deal,requestDto);
        return updatedAttibutes;
    }

    public MultipleDealsResponse getMultipleDealsResponse(Page<Deal> page) {
        MultipleDealsResponse response = new MultipleDealsResponse();
        response.setTotalCount(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        List<DealCardDetails> deals = page.stream()
                .map(this::getDealCardDetails)
                .collect(Collectors.toList());
        response.setDeals(deals);
        return response;
    }

    public DealCardDetails getDealCardDetails(Deal deal) {
        DealCardDetails dealCardDetails = new DealCardDetails();
        mapperUtils.updateResponseFromEntity(deal,dealCardDetails);
        dealCardDetails.setDealId(deal.getId());
        dealCardDetails.setDealName(deal.getName());
        dealCardDetails.setPartyId(deal.getParty().getId());
        dealCardDetails.setPartyName(deal.getParty().getPartyName());
        List<User> coOwnersList = deal.getCoOwners();
        String coOwners = String.join(", ",coOwnersList.stream().map( user -> user.getEmail()).collect(Collectors.toList()));
        dealCardDetails.setDealOwners(coOwners);
        return dealCardDetails;
    }

    public DealDetailResponse getDealDetailResponse(Deal deal, User owner, List<User> coOwners) {
        DealDetailResponse dealDetailResponse = new DealDetailResponse();
        DealCardDetails cardDetails = getDealCardDetails(deal);
        DealProductDetails productDetails = new DealProductDetails();
        mapperUtils.updateResponseFromEntity(deal, productDetails);
        DealCommonDetails commonDetails = new DealCommonDetails();
        mapperUtils.updateResponseFromEntity(deal, commonDetails);
        DealAdditionalDetails additionalDetails = new DealAdditionalDetails();
        mapperUtils.updateResponseFromEntity(deal, additionalDetails);
        DealAuthorizationDetails authorizationDetails = new DealAuthorizationDetails();
        authorizationDetails.setOwner(getDealUserDetails(owner));
        List<DealUserDetails> coOwnerDetails = coOwners.stream().map(this::getDealUserDetails)
                .collect(Collectors.toList());
        authorizationDetails.setCoOwners(coOwnerDetails);
        dealDetailResponse.setCardDetails(cardDetails);
        dealDetailResponse.setProductDetails(productDetails);
        dealDetailResponse.setCommonDetails(commonDetails);
        dealDetailResponse.setAdditionalDetails(additionalDetails);
        dealDetailResponse.setAuthorizationDetails(authorizationDetails);
        return dealDetailResponse;
     }

    public DealUserDetails getDealUserDetails(User user) {
        if(user == null) return null;
        DealUserDetails details = new DealUserDetails();
        mapperUtils.updateResponseFromEntity(user, details);
        return details;
    }

    public DealDetailResponse2 getDealDetailResponse2(Deal deal) {
        DealDetailResponse2 dealDetailResponse = new DealDetailResponse2();
        DealProductDetails productDetails = new DealProductDetails();
        mapperUtils.updateResponseFromEntity(deal, productDetails);
        DealCommonDetails commonDetails = new DealCommonDetails();
        mapperUtils.updateResponseFromEntity(deal, commonDetails);
        DealAdditionalDetails additionalDetails = new DealAdditionalDetails();
        mapperUtils.updateResponseFromEntity(deal, additionalDetails);
        dealDetailResponse.setProductDetails(productDetails);
        dealDetailResponse.setCommonDetails(commonDetails);
        dealDetailResponse.setAdditionalDetails(additionalDetails);
        return dealDetailResponse;
    }
}
