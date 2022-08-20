package com.example.art.dto.response;

import com.example.art.dto.response.inner.AttachmentDetails;
import com.example.art.dto.response.inner.DropdownKeyDetails;
import lombok.Data;

import java.util.List;

@Data
public class DropdownKeysResponse {

    private List<DropdownKeyDetails> keys;

}
