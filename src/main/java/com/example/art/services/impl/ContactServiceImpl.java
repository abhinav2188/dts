package com.example.art.services.impl;

import com.example.art.dto.mapper.ContactMapper;
import com.example.art.dto.request.CreateContactRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.Contact;
import com.example.art.model.Deal;
import com.example.art.model.enums.UserRole;
import com.example.art.repository.ContactRepository;
import com.example.art.repository.DealRepository;
import com.example.art.services.ContactService;
import com.example.art.utils.Constants;
import com.example.art.utils.MessageUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private ContactMapper mapper;

    @Override
    public BaseResponse addContact(Long dealId, CreateContactRequest request) throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        checkUserAuthorization(deal);

        Contact contact = mapper.createContact(request);

        contact.setDeal(deal);

        Contact saved = contactRepository.save(contact);

        return BaseResponse.builder()
                .responseMsg(MessageUtils.successPostMessage("Contact"))
                .status(HttpStatus.CREATED)
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
