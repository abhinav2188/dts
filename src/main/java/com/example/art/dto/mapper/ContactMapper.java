package com.example.art.dto.mapper;

import com.example.art.dto.request.CreateContactRequest;
import com.example.art.dto.response.ContactsResponse;
import com.example.art.dto.response.inner.ContactDetails;
import com.example.art.model.Contact;
import com.example.art.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactMapper {

    @Autowired
    private MapperUtils mapperUtils;

    public Contact createContact(CreateContactRequest request) {
        Contact contact = new Contact();
        mapperUtils.createEntity(contact, request);
        return contact;
    }

    public ContactsResponse getContactsResponse(Page<Contact> contacts) {
        ContactsResponse response = new ContactsResponse();
        response.setTotalCount(contacts.getTotalElements());
        response.setTotalPageCount(contacts.getTotalPages());
        List<ContactDetails> contactDetailsList = contacts.stream()
                .map(this::getContactDetails)
                .collect(Collectors.toList());
        response.setContacts(contactDetailsList);
        return response;
    }

    public ContactDetails getContactDetails(Contact contact) {
        ContactDetails details = new ContactDetails();
        mapperUtils.updateResponseFromEntity(contact, details);
        return details;
    }

}
