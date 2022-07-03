package com.example.art.controllers;

import com.example.art.dto.BaseResponse;
import com.example.art.dto.request.AddPartyRequestDto;
import com.example.art.services.PartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/ext/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @PostMapping
    public BaseResponse addParty(@RequestBody AddPartyRequestDto addPartyRequestDto){
        return partyService.addNewParty(addPartyRequestDto);
    }
}
