package com.example.art.services.impl;

import com.example.art.dto.mapper.DropdownMapper;
import com.example.art.dto.response.inner.DropdownKeyValuesDetails;
import com.example.art.model.enums.DropdownType;
import com.example.art.model.views.*;
import com.example.art.repository.*;
import com.example.art.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FunctionalInterface
interface GetDerivedDropdownValue{
    DropdownKeyValuesDetails getDetails(DropdownType dropdownType);
}

@Component
@Slf4j
public class DerivedDropdownService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private BrochureRepository brochureRepository;

    @Autowired
    private DropdownMapper dropdownMapper;

    private Map<DropdownType, GetDerivedDropdownValue> map;

    @PostConstruct
    public void init(){
        map = new HashMap<>();
        map.put(DropdownType.PARTY, getPartyNames);
        map.put(DropdownType.MEETING_HANDLER, getHandlerEmails);
        map.put(DropdownType.MEETING_CONSULTANT, getConsultantNames);
        map.put(DropdownType.MEETING_CONTACT, getContactNames);
        map.put(DropdownType.BROCHURES_TYPE, getBrochuresNames);
        map.put(DropdownType.DEAL_RECIPIENTS, getRecipients);
    }


    public DropdownKeyValuesDetails getDerivedDropdownDetails(DropdownType dropdownType){
        if(!map.containsKey(dropdownType))
            return new DropdownKeyValuesDetails(dropdownType.name(),dropdownType.getFormType().name(), new ArrayList<>());
        return map.get(dropdownType).getDetails(dropdownType);
    }

    private GetDerivedDropdownValue getRecipients = (dropdownType) -> {
        // returning contacts and consultants emails of a deal
        Long dealId;
        try {
            dealId = Long.parseLong(MDC.get(Constants.DEAL_ID));
        }catch (Exception e){
            log.error("invalid dealId passed");
            return null;
        }
        List<ContactDropdownView> viewList = contactRepository.findByDeal_Id(dealId);
        List<ConsultantDropdownView> viewList1 = consultantRepository.findByDeal_Id(dealId);

        DropdownKeyValuesDetails details = dropdownMapper.getContactDropdownKeyValuesDetails(dropdownType, viewList);
        DropdownKeyValuesDetails details1 = dropdownMapper.getConsultantDropdownKeyValuesDetails(dropdownType, viewList1);
        details.getValues().addAll(details1.getValues());

        return details;
    };

    private GetDerivedDropdownValue getBrochuresNames = (dropdownType) -> {
        List<BrochureDropdownView> viewList = brochureRepository.findByIsActiveTrueOrderByUpdateTimestampDesc();
        return dropdownMapper.getBrochureDropdownKeyValuesDetails(dropdownType,viewList);
    };


    private GetDerivedDropdownValue getPartyNames = (dropdownType) -> {
        List<PartyDropdownView> viewList = partyRepository.findByIsActiveTrueOrderByCreateTimestampDesc();
        return dropdownMapper.getPartyDropdownKeyValuesDetails(dropdownType,viewList);
    };

    private GetDerivedDropdownValue getHandlerEmails = (dropdownType -> {
        Long dealId;
        try {
            dealId = Long.parseLong(MDC.get(Constants.DEAL_ID));
        }catch (Exception e){
            log.error("invalid dealId passed");
            return null;
        }
        List<UserHandlerDropdownView> viewList = userRepository.findByIsActiveTrueAndCoOwnedDeals_Id(dealId);
        return dropdownMapper.getHandlerDropdownKeyValuesDetails(dropdownType, viewList);
    });

    private GetDerivedDropdownValue getContactNames = (dropdownType -> {
        Long dealId;
        try {
            dealId = Long.parseLong(MDC.get(Constants.DEAL_ID));
        }catch (Exception e){
            log.error("invalid dealId passed");
            return null;
        }
        List<ContactDropdownView> viewList = contactRepository.findByDeal_Id(dealId);
        return dropdownMapper.getContactDropdownKeyValuesDetails(dropdownType, viewList);
    });

    private GetDerivedDropdownValue getConsultantNames = (dropdownType -> {
        Long dealId;
        try {
            dealId = Long.parseLong(MDC.get(Constants.DEAL_ID));
        }catch (Exception e){
            log.error("invalid dealId passed");
            return null;
        }
        List<ConsultantDropdownView> viewList = consultantRepository.findByDeal_Id(dealId);
        return dropdownMapper.getConsultantDropdownKeyValuesDetails(dropdownType, viewList);
    });


}
