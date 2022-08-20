package com.example.art.services;

import com.example.art.dto.request.CreateContactRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.ContactsResponse;
import com.example.art.dto.response.ContactsResponse2;
import com.example.art.dto.response.inner.ContactDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;

public interface ContactService {

    BaseResponse<ContactDetails> addContact(Long dealId, CreateContactRequest request) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse<ContactsResponse> getDealContacts(Long dealId, int pageNo, int pageSize) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse deleteContact(Long contactId) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse<ContactsResponse2> getAllContacts(int pageNo, int pageSize);
}
