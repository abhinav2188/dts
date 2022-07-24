package com.example.art.dto.mapper;

import com.example.art.dto.request.CreateContactRequest;
import com.example.art.model.Contact;
import com.example.art.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    @Autowired
    private MapperUtils mapperUtils;

    public Contact createContact(CreateContactRequest request) {
        Contact contact = new Contact();
        mapperUtils.createEntity(contact, request);
        return contact;
    }
}
