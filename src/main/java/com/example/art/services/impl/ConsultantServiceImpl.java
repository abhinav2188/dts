package com.example.art.services.impl;

import com.example.art.dto.mapper.ConsultantMapper;
import com.example.art.dto.request.CreateConsultantRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.ConsultantsResponse;
import com.example.art.dto.response.SuccessDeleteResponse;
import com.example.art.dto.response.inner.ConsultantDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.Consultant;
import com.example.art.model.Deal;
import com.example.art.model.enums.UserRole;
import com.example.art.repository.ConsultantRepository;
import com.example.art.repository.DealRepository;
import com.example.art.services.ConsultantService;
import com.example.art.utils.Constants;
import com.example.art.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsultantServiceImpl implements ConsultantService {
    
    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private ConsultantMapper mapper;

    @Override
    public BaseResponse<ConsultantDetails> addConsultant(Long dealId, CreateConsultantRequest request) throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        checkUserAuthorization(deal);

        Consultant consultant = mapper.createConsultant(request);

        consultant.setDeal(deal);

        Consultant saved = consultantRepository.save(consultant);

        return BaseResponse.<ConsultantDetails>builder()
                .responseMsg(MessageUtils.successPostMessage("Consultant"))
                .status(HttpStatus.CREATED)
                .data(mapper.getConsultantDetails(saved))
                .build();
    }

    @Override
    public BaseResponse<ConsultantsResponse> getDealConsultants(Long dealId, int pageNo, int pageSize)
            throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        checkUserAuthorization(deal);

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("name").descending());
        Page<Consultant> consultants = consultantRepository.findAllByDealId(dealId,pageable);

        ConsultantsResponse response = mapper.getConsultantsResponse(consultants);

        return BaseResponse.<ConsultantsResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successGetMessage("Consultant",response.getConsultants().size()))
                .data(response)
                .build();

    }

    @Override
    public BaseResponse<SuccessDeleteResponse> deleteConsultant(Long consultantId) throws EntityNotFoundException, NoAuthorizationException {

        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow( () -> new EntityNotFoundException("Consultant","id",consultantId));

        Deal deal = consultant.getDeal();

        checkUserAuthorization(deal);

        consultantRepository.delete(consultant);

        return BaseResponse.<SuccessDeleteResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successDeleteMessage("Consultant"))
                .data(new SuccessDeleteResponse(consultantId))
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
