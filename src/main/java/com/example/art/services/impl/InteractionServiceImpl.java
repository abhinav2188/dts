package com.example.art.services.impl;

import com.example.art.dto.mapper.InteractionMapper;
import com.example.art.dto.request.CreateInteractionRequest;
import com.example.art.dto.request.UpdateInteractionRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.InteractionsResponse;
import com.example.art.dto.response.inner.InteractionDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.Interaction;
import com.example.art.model.Deal;
import com.example.art.model.enums.DealSubject;
import com.example.art.model.enums.UserRole;
import com.example.art.repository.InteractionRepository;
import com.example.art.repository.DealRepository;
import com.example.art.services.DealHistoryService;
import com.example.art.services.InteractionService;
import com.example.art.utils.Constants;
import com.example.art.utils.MessageUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class InteractionServiceImpl implements InteractionService {

    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private InteractionMapper mapper;

    @Autowired
    private DealHistoryService dealHistoryService;

    @Override
    public BaseResponse<InteractionDetails> addInteraction(Long dealId, CreateInteractionRequest request) throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        checkUserAuthorization(deal);

        Interaction interaction = mapper.createInteraction(request);

        interaction.setDealStage(deal.getDealStage());
        interaction.setDeal(deal);

        Interaction saved = interactionRepository.save(interaction);

        InteractionDetails response = mapper.getInteractionDetails(saved);

        dealHistoryService.addDealHistory(deal.getId(), DealSubject.ADDED_DEAL_INTERACTION, response);

        return BaseResponse.<InteractionDetails>builder()
                .responseMsg(MessageUtils.successPostMessage("Interaction"))
                .status(HttpStatus.CREATED)
                .data(response)
                .build();
    }

    @Override
    public BaseResponse<InteractionsResponse> getDealInteractions(Long dealId, int pageNo, int pageSize)
            throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        checkUserAuthorization(deal);

        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by("createTimestamp").descending());
        Page<Interaction> interactions = interactionRepository.findAllByDealId(dealId,pageable);

        InteractionsResponse response = mapper.getInteractionsResponse(interactions);

        return BaseResponse.<InteractionsResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successGetMessage("Interaction",response.getInteractions().size()))
                .data(response)
                .build();

    }

    @Override
    public BaseResponse deleteInteraction(Long interactionId) throws EntityNotFoundException, NoAuthorizationException {

        Interaction interaction = interactionRepository.findById(interactionId)
                .orElseThrow( () -> new EntityNotFoundException("Interaction","id",interactionId));

        Deal deal = interaction.getDeal();

        checkUserAuthorization(deal);

        interactionRepository.delete(interaction);

        dealHistoryService.addDealHistory(deal.getId(), DealSubject.DELETED_DEAL_INTERACTION,
                mapper.getInteractionDetails(interaction));

        return BaseResponse.builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successDeleteMessage("Interaction"))
                .build();

    }

    @Override
    public BaseResponse updateInteraction(Long interactionId, UpdateInteractionRequest request)
            throws EntityNotFoundException, NoAuthorizationException {

        Interaction interaction = interactionRepository.findById(interactionId)
                .orElseThrow( () -> new EntityNotFoundException("Interaction","id",interactionId));

        Deal deal = interaction.getDeal();

        checkUserAuthorization(deal);

        mapper.updateInteraction(interaction, request);

        interactionRepository.save(interaction);

        dealHistoryService.addDealHistory(deal.getId(), DealSubject.UPDATED_DEAL_INTERACTION, request);

        return BaseResponse.builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successUpdateMessage("Interaction"))
                .build();
    }

    private boolean isUserAdmin() {
        String roles = MDC.get(Constants.USER_ROLES);
        if(roles != null)
            return roles.contains(UserRole.ADMIN.name());
        return false;
    }

    private Long getCurrentUserId(){
        Long userId = Long.parseLong(MDC.get(Constants.USER_ID));
        return userId;
    }

    private void checkUserAuthorization(Deal deal) throws NoAuthorizationException {
        if(isUserAdmin()) return;
        Long userId = getCurrentUserId();
        deal.getCoOwners().stream()
                .filter(user -> user.getId().equals(userId))
                .findAny()
                .orElseThrow(
                        () -> new NoAuthorizationException(MessageUtils.noAuthorization("Deal")));
    }

}
