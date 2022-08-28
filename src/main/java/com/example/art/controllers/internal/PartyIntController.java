package com.example.art.controllers.internal;

import com.example.art.dto.request.UpdatePartyRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.PartiesDetailResponse;
import com.example.art.dto.response.PartyResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.services.PartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/int/party")
public class PartyIntController {

    @Autowired
    private PartyService partyService;

    @GetMapping(path = "/all")
    public BaseResponse<PartiesDetailResponse> getAllPartiesDetails(
            @RequestParam(name = "pageNo") Integer pageNo,
            @RequestParam(name = "pageCount", defaultValue = "10", required = false) Integer pageCount ){
        return partyService.getAllParties(pageNo, pageCount);
    }

    @PutMapping(path = "/{id}")
    public BaseResponse<PartyResponse> updatePartyDetails(@PathVariable Long id, @RequestBody UpdatePartyRequest updatePartyRequest)
            throws EntityNotFoundException {
        log.info("update party request body: {}",String.valueOf(updatePartyRequest));
        return partyService.updateDetails(id,updatePartyRequest);
    }

}
