package com.example.art.dto.request;

import lombok.Data;

@Data
public class UpdateProductDetailsRequest {

    private String productType;

    private String subCategoryProduct;

    private String unitOfQuantity;

    private String orderSizeFactor;

    private String typeOfWork;

    private String roadDetails;

}
