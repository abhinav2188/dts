package com.example.art.dto.mapper;

import com.example.art.dto.request.CreatePartyRequest;
import com.example.art.dto.request.UpdatePartyRequest;
import com.example.art.dto.response.PartyResponse;
import com.example.art.dto.response.inner.PartyDetails;
import com.example.art.model.Party;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PartyMapper {

    public PartyResponse getPartyResponse(Party party1) {
        PartyResponse response = new PartyResponse();
        response.setEmail(party1.getEmail());
        response.setAddress(party1.getAddress());
        response.setAuthority(party1.getAuthority());
        response.setId(party1.getId());
        response.setPartyName(party1.getPartyName());
        response.setMobile(party1.getMobile());
        return response;
    }

    public Party createParty(CreatePartyRequest request){
        Party party = new Party();
        party.setPartyName(request.getPartyName());
        party.setIsActive(true);
        party.setAddress(request.getAddress());
        party.setAuthority(request.getAuthority());
        party.setMobile(request.getMobile());
        party.setEmail(request.getEmail());
        return party;
    }

    public void update(Party party, UpdatePartyRequest request) {
        party.setPartyName(request.getPartyName());
        party.setIsActive(request.getIsActive());
        party.setAddress(request.getAddress());
        party.setAuthority(request.getAuthority());
        party.setMobile(request.getMobile());
        party.setEmail(request.getEmail());
        // todo : add update msg logs
    }

    public PartyDetails getPartyDetails(Party party1){
        PartyDetails response= new PartyDetails();
        response.setEmail(party1.getEmail());
        response.setAddress(party1.getAddress());
        response.setAuthority(party1.getAuthority());
        response.setId(party1.getId());
        response.setPartyName(party1.getPartyName());
        response.setMobile(party1.getMobile());
        response.setIsActive(party1.getIsActive());
        response.setDealsCount(party1.getDeals().size());
        return response;
    }

    public List<PartyDetails> getPartyDetailsList(Page<Party> page) {
        return page.stream()
                .map(this::getPartyDetails)
                .collect(Collectors.toList());
    }
}
