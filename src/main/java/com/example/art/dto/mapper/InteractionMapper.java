package com.example.art.dto.mapper;

import com.example.art.dto.request.CreateInteractionRequest;
import com.example.art.dto.request.UpdateInteractionRequest;
import com.example.art.dto.response.InteractionsResponse;
import com.example.art.dto.response.inner.InteractionDetails;
import com.example.art.model.Interaction;
import com.example.art.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InteractionMapper {

    @Autowired
    private MapperUtils mapperUtils;

    public Interaction createInteraction(CreateInteractionRequest request) {
        Interaction interaction = new Interaction();
        mapperUtils.createEntity(interaction, request);
        return interaction;
    }

    public InteractionsResponse getInteractionsResponse(Page<Interaction> interactions) {
        InteractionsResponse response = new InteractionsResponse();
        response.setTotalCount(interactions.getTotalElements());
        response.setTotalPageCount(interactions.getTotalPages());
        List<InteractionDetails> interactionDetailsList = interactions.stream()
                .map(this::getInteractionDetails)
                .collect(Collectors.toList());
        response.setInteractions(interactionDetailsList);
        return response;
    }

    public InteractionDetails getInteractionDetails(Interaction interaction) {
        InteractionDetails details = new InteractionDetails();
        mapperUtils.updateResponseFromEntity(interaction, details);
        return details;
    }

    public void updateInteraction(Interaction interaction, UpdateInteractionRequest request) {
        List<String> updates = new ArrayList<>();
        mapperUtils.updateEntity(interaction,request,updates);
    }
}
