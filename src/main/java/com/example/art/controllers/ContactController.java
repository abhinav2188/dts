package com.example.art.controllers;

import com.example.art.dto.request.CreateContactRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.ContactsResponse;
import com.example.art.dto.response.inner.ContactDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/ext/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/{dealId}")
    public BaseResponse<ContactDetails> addDealContact(@PathVariable Long dealId,
                                                       @Valid @RequestBody CreateContactRequest request)
            throws NoAuthorizationException, EntityNotFoundException {
        return contactService.addContact(dealId,request);
    }

    @GetMapping("/{dealId}")
    public BaseResponse<ContactsResponse> getDealContacts(@PathVariable Long dealId,
                                                          @RequestParam(name = "pageNo") int pageNo,
                                                          @RequestParam(name = "pageSize", defaultValue = "20") int pageSize)
            throws NoAuthorizationException, EntityNotFoundException {
        return contactService.getDealContacts(dealId, pageNo, pageSize);
    }

    @DeleteMapping("/{contactId}")
    public BaseResponse deleteContact(@PathVariable Long contactId)
            throws NoAuthorizationException, EntityNotFoundException {
        return contactService.deleteContact(contactId);
    }

}
