package com.example.art.controllers.internal;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.ContactsResponse2;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.services.ContactService;
import com.example.art.services.helper.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/int/contacts")
public class ContactIntController {

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private ContactService contactService;

    @GetMapping
    public BaseResponse<ContactsResponse2> getAllContacts(@RequestParam int pageNo,
                                                          @RequestParam(required = false, defaultValue = "20") int pageSize) throws NoAuthorizationException {
        serviceUtils.assertUserAdmin();
        return contactService.getAllContacts(pageNo,pageSize);
    }

}
