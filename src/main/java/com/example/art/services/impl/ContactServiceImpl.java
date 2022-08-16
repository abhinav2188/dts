package com.example.art.services.impl;

import com.example.art.dto.mapper.ContactMapper;
import com.example.art.dto.request.CreateContactRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.ContactsResponse;
import com.example.art.dto.response.inner.ContactDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.Contact;
import com.example.art.model.Deal;
import com.example.art.model.DealHistory;
import com.example.art.model.enums.DealSubject;
import com.example.art.model.enums.UserRole;
import com.example.art.repository.ContactRepository;
import com.example.art.repository.DealRepository;
import com.example.art.services.ContactService;
import com.example.art.services.DealHistoryService;
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
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private ContactMapper mapper;

    @Autowired
    private DealHistoryService dealHistoryService;

    @Override
    public BaseResponse<ContactDetails> addContact(Long dealId, CreateContactRequest request) throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        checkUserAuthorization(deal);

        Contact contact = mapper.createContact(request);

        contact.setDeal(deal);

        Contact saved = contactRepository.save(contact);

        ContactDetails response = mapper.getContactDetails(saved);
        dealHistoryService.addDealHistory(deal.getId(), DealSubject.ADDED_DEAL_CONTACT, response);

        return BaseResponse.<ContactDetails>builder()
                .responseMsg(MessageUtils.successPostMessage("Contact"))
                .data(response)
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse<ContactsResponse> getDealContacts(Long dealId, int pageNo, int pageSize)
            throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        checkUserAuthorization(deal);

        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by("name").descending());
        Page<Contact> contacts = contactRepository.findAllByDealId(dealId,pageable);

        ContactsResponse response = mapper.getContactsResponse(contacts);

        return BaseResponse.<ContactsResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successGetMessage("Contact",response.getContacts().size()))
                .data(response)
                .build();

    }

    @Override
    public BaseResponse deleteContact(Long contactId) throws EntityNotFoundException, NoAuthorizationException {

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow( () -> new EntityNotFoundException("Contact","id",contactId));

        Deal deal = contact.getDeal();

        checkUserAuthorization(deal);

        contactRepository.delete(contact);

        dealHistoryService.addDealHistory(deal.getId(), DealSubject.DELETED_DEAL_CONTACT, mapper.getContactDetails(contact));

        return BaseResponse.builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successDeleteMessage("Contact"))
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
