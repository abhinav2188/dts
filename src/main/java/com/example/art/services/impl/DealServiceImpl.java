package com.example.art.services.impl;

import com.example.art.dto.CreateDealResponse;
import com.example.art.dto.mapper.DealMapper;
import com.example.art.dto.request.CreateDealRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.exceptions.InvalidFieldException;
import com.example.art.model.Deal;
import com.example.art.model.Party;
import com.example.art.model.User;
import com.example.art.repository.DealRepository;
import com.example.art.repository.PartyRepository;
import com.example.art.repository.UserRepository;
import com.example.art.services.DealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DealServiceImpl implements DealService {

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private DealMapper dealMapper;

    @Override
    public BaseResponse<CreateDealResponse> createDeal(CreateDealRequest requestDto)
            throws InvalidFieldException, DuplicateEntryException {

        validateCreateDealRequest(requestDto);

        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                ()-> new InvalidFieldException("userId"));

        Party party = partyRepository.findById(requestDto.getPartyId()).orElseThrow(
                ()-> new InvalidFieldException("partyId"));

        Deal deal = dealMapper.getDeal(requestDto);

        user.addDeal(deal);
        party.addDeal(deal);

        Deal saved = dealRepository.save(deal);

        log.info("new deal created with name {}, with party {}, by user {}",
                deal.getName(), party.getPartyName(), user.getEmail());

        return BaseResponse.<CreateDealResponse>builder()
                .responseMsg("deal created")
                .status(HttpStatus.CREATED)
                .data(dealMapper.getCreateDealResponse(saved))
                .build();
    }

    private void validateCreateDealRequest(CreateDealRequest requestDto) throws InvalidFieldException, DuplicateEntryException {
        if(dealRepository.existsByName(requestDto.getDealName())){
            throw new DuplicateEntryException("dealName");
        }
//        if(userRepository.existsById(requestDto.getUserId()))
//            throw new InvalidFieldException("userId");
//        if(partyRepository.existsById(requestDto.getPartyId()))
//            throw new InvalidFieldException("partyId");
    }

}
