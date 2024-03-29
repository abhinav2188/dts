package com.example.art.services;

import com.example.art.dto.request.CreatePartyRequest;
import com.example.art.dto.request.UpdatePartyRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.PartiesDetailResponse;
import com.example.art.dto.response.PartyResponse;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.exceptions.EntityNotFoundException;

public interface PartyService {

    BaseResponse addNewParty(CreatePartyRequest addPartyRequestDto) throws DuplicateEntryException;

    BaseResponse<PartyResponse> getPartyDetails(Long id) throws EntityNotFoundException;

    BaseResponse<PartyResponse> updateDetails(Long id, UpdatePartyRequest updatePartyRequest) throws EntityNotFoundException;

    BaseResponse<PartiesDetailResponse> getAllParties(Integer pageNo, Integer pageCount);
}
