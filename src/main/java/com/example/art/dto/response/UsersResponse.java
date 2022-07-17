package com.example.art.dto.response;

import com.example.art.dto.response.inner.UserCardDetails;
import lombok.Data;

import java.util.List;

@Data
public class UsersResponse {

    private List<UserCardDetails> usersList;

    long totalUsers;

    int totalPages;

}
