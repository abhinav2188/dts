package com.example.art.services.impl;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.request.AddPartyRequestDto;
import com.example.art.model.Party;
import com.example.art.repository.PartyRepository;
import com.example.art.services.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PartyServiceImpl implements PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Override
    public BaseResponse addNewParty(AddPartyRequestDto addPartyRequestDto) {
        Party party = partyRepository.save(new Party(addPartyRequestDto));
        if(party != null){
            return BaseResponse.builder().status(HttpStatus.ACCEPTED)
                    .responseMsg("Party Added Successfully")
                    .data(party)
                    .build();
        }
        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg("Party was not added due to some error")
                .build();
    }
}
