package com.example.art.dto.response;

import com.example.art.model.views.ContactExcelView;
import lombok.Data;

import java.util.List;

@Data
public class ContactsResponse2 {

    private long totalCount;

    private int totalPageCount;

    List<ContactExcelView> contacts;

}
