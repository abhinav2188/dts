package com.example.art.dto.response;

import com.example.art.dto.response.inner.AttachmentDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AttachmentsResponse {

    private int totalCount;

    private List<AttachmentDetails> info;

}
