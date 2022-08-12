package com.example.art.controllers;

import com.example.art.dto.request.CreatePartyRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.PartyResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.services.PartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/ext/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @PostMapping
    public BaseResponse<PartyResponse> addParty(@RequestBody CreatePartyRequest createPartyRequest){
        return partyService.addNewParty(createPartyRequest);
    }

    @GetMapping("/{id}")
    public BaseResponse<PartyResponse> getPartyDetails(@PathVariable Long id) throws EntityNotFoundException {
        return partyService.getPartyDetails(id);
    }
}
