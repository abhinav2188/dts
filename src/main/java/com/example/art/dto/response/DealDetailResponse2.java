package com.example.art.dto.response;

import com.example.art.dto.response.inner.DealAdditionalDetails;
import com.example.art.dto.response.inner.DealCommonDetails;
import com.example.art.dto.response.inner.DealProductDetails;
import lombok.Data;

@Data
public class DealDetailResponse2 {

    private DealProductDetails productDetails;

    private DealCommonDetails commonDetails;

    private DealAdditionalDetails additionalDetails;

}
