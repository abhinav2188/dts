package com.example.art.services;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.request.AddPartyRequestDto;

public interface PartyService {

    BaseResponse addNewParty(AddPartyRequestDto addPartyRequestDto);

}
