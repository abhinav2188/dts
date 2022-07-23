package com.example.art.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateDealAuthorizationRequest {

    private Long ownerId;

    private List<Long> coOwnerIds;

}
