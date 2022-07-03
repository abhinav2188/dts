package com.example.art.services;

import com.example.art.dto.BaseResponse;
import com.example.art.dto.request.AddPartyRequestDto;

public interface PartyService {

    BaseResponse addNewParty(AddPartyRequestDto addPartyRequestDto);

}
