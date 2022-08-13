package com.example.art.dto.request;

import lombok.Data;

@Data
public class DealQueryRequest {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

}
