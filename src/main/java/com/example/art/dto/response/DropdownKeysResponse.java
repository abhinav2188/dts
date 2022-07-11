package com.example.art.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class DropdownKeysResponse {

    private List<DropdownKeyDetails> keys;

}
