package com.example.art.controllers.internal;

import com.example.art.dto.request.UpdatePartyRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.PartiesDetailResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.services.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse updatePartyDetails(@PathVariable Long id, @RequestBody UpdatePartyRequest updatePartyRequest)
            throws EntityNotFoundException {
        return partyService.updateDetails(id,updatePartyRequest);
    }

}
