package com.example.art.dto.response.inner;

import lombok.Data;

import java.util.List;

@Data
public class DealAuthorizationDetails {

    private DealUserDetails owner;

    List<DealUserDetails> coOwners;

}
