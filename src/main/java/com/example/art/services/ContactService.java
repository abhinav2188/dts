package com.example.art.services;

import com.example.art.dto.request.CreateContactRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;

public interface ContactService {

    BaseResponse addContact(Long dealId, CreateContactRequest request) throws EntityNotFoundException, NoAuthorizationException;

}
