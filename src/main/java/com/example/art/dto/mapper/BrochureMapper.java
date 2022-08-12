package com.example.art.dto.mapper;

import com.example.art.dto.response.BrochureResponse;
import com.example.art.dto.response.BrochuresResponse;
import com.example.art.model.Brochure;
import com.example.art.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BrochureMapper {

    @Autowired
    private MapperUtils mapperUtils;

    public BrochureResponse getBrochureResponse(Brochure entity) {
        BrochureResponse response = new BrochureResponse();
        mapperUtils.updateResponseFromEntity(entity, response);
        return response;
    }

    public BrochuresResponse getBrochuresResponse(Page<Brochure> brochurePage) {
        BrochuresResponse response = new BrochuresResponse();
        response.setTotalCount(brochurePage.getTotalElements());
        response.setTotalPageCount(brochurePage.getTotalPages());
        List<BrochureResponse> brochures = brochurePage.stream()
                .map(this::getBrochureResponse)
                .collect(Collectors.toList());
        response.setBrochures(brochures);
        return response;
    }

}
