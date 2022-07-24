package com.example.art.dto.mapper;

import com.example.art.dto.request.CreateConsultantRequest;
import com.example.art.dto.response.ConsultantsResponse;
import com.example.art.dto.response.inner.ConsultantDetails;
import com.example.art.model.Consultant;
import com.example.art.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsultantMapper {

    @Autowired
    private MapperUtils mapperUtils;

    public Consultant createConsultant(CreateConsultantRequest request) {
        Consultant consultant = new Consultant();
        mapperUtils.createEntity(consultant, request);
        return consultant;
    }

    public ConsultantsResponse getConsultantsResponse(Page<Consultant> consultants) {
        ConsultantsResponse response = new ConsultantsResponse();
        response.setTotalCount(consultants.getTotalElements());
        response.setTotalPageCount(consultants.getTotalPages());
        List<ConsultantDetails> consultantDetailsList = consultants.stream()
                .map(this::getConsultantDetails)
                .collect(Collectors.toList());
        response.setConsultants(consultantDetailsList);
        return response;
    }

    private ConsultantDetails getConsultantDetails(Consultant consultant) {
        ConsultantDetails details = new ConsultantDetails();
        mapperUtils.updateResponseFromEntity(consultant, details);
        return details;
    }

}
