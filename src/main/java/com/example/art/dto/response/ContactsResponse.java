package com.example.art.dto.response;

import com.example.art.dto.response.inner.ContactDetails;
import lombok.Data;

import java.util.List;

@Data
public class ContactsResponse {

    private long totalCount;

    private int totalPageCount;

    private List<ContactDetails> contacts;

}
