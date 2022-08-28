package com.example.art.services.impl;

import com.example.art.dto.mapper.PartyMapper;
import com.example.art.dto.request.CreatePartyRequest;
import com.example.art.dto.request.UpdatePartyRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.PartiesDetailResponse;
import com.example.art.dto.response.PartyResponse;
import com.example.art.dto.response.inner.PartyDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.model.Party;
import com.example.art.repository.PartyRepository;
import com.example.art.services.PartyService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.util.List;

@Slf4j
@Service
public class PartyServiceImpl implements PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyMapper partyMapper;

    @Override
    public BaseResponse<PartyResponse> addNewParty(CreatePartyRequest requestDto) {
        Party party = partyMapper.createParty(requestDto);
        Party savedParty = partyRepository.save(party);
        PartyResponse partyResponse = partyMapper.getPartyResponse(party);
        if(savedParty != null){
            return BaseResponse.<PartyResponse>builder().status(HttpStatus.ACCEPTED)
                    .status(HttpStatus.OK)
                    .responseMsg("Party Added Successfully")
                    .data(partyResponse)
                    .build();
        }
        return BaseResponse.<PartyResponse>builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg("Party was not added due to some error")
                .build();
    }

    @Override
    public BaseResponse<PartyResponse> getPartyDetails(Long id) throws EntityNotFoundException {

        PartyResponse party = partyRepository.findById(id).filter(party1 -> party1.getIsActive())
                .map(party1 -> partyMapper.getPartyResponse(party1))
                .orElseThrow(() -> new EntityNotFoundException("Party","id",id));

        log.info("party "+party);

        return BaseResponse.<PartyResponse>builder()
                .status(HttpStatus.OK)
                .responseCode(String.valueOf(HttpStatus.OK.value()))
                .responseMsg("fetched party details")
                .data(party)
                .build();
    }

    @Override
    public BaseResponse<PartyResponse> updateDetails(Long id, UpdatePartyRequest updatePartyRequest) throws EntityNotFoundException {
        Party party = partyRepository.findById(id)
                .orElseThrow(() ->new EntityNotFoundException("Party","id",id));
        partyMapper.update(party, updatePartyRequest);
        Party saved = partyRepository.save(party);
        return BaseResponse.<PartyResponse>builder()
                .status(HttpStatus.OK)
                .responseCode(String.valueOf(HttpStatus.OK.value()))
                .responseMsg("updated party details")
                .data(partyMapper.getPartyResponse(saved))
                .build();
    }

    @Override
    public BaseResponse<PartiesDetailResponse> getAllParties(Integer pageNo, Integer pageSize) {

        PartiesDetailResponse response = new PartiesDetailResponse();

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("updateTimestamp").descending());
        Page<Party> page = partyRepository.findAll(pageable);

        response.setTotalCount(page.getTotalElements());
        response.setTotalPagesCount(page.getTotalPages());

        List<PartyDetails> partyList = partyMapper.getPartyDetailsList(page);
        response.setPartyList(partyList);

        return BaseResponse.<PartiesDetailResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg("fetched "+partyList.size()+" party details")
                .data(response)
                .build();

    }
}
