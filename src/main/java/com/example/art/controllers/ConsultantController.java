package com.example.art.controllers;

import com.example.art.dto.request.CreateConsultantRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.ConsultantsResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.services.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/ext/consultants")
public class ConsultantController {

    @Autowired
    private ConsultantService consultantService;

    @PostMapping("/{dealId}")
    public BaseResponse addDealConsultant(@PathVariable Long dealId,
                                       @Valid @RequestBody CreateConsultantRequest request)
            throws NoAuthorizationException, EntityNotFoundException {
        return consultantService.addConsultant(dealId,request);
    }

    @GetMapping("/{dealId}")
    public BaseResponse<ConsultantsResponse> getDealConsultants(@PathVariable Long dealId,
                                                                @RequestParam(name = "pageNo") int pageNo,
                                                                @RequestParam(name = "pageSize", defaultValue = "20") int pageSize)
            throws NoAuthorizationException, EntityNotFoundException {
        return consultantService.getDealConsultants(dealId, pageNo, pageSize);
    }

    @DeleteMapping("/{consultantId}")
    public BaseResponse deleteConsultant(@PathVariable Long consultantId)
            throws NoAuthorizationException, EntityNotFoundException {
        return consultantService.deleteConsultant(consultantId);
    }

}
