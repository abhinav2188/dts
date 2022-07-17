package com.example.art.dto.response.inner;

import com.example.art.model.DropdownValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DropdownValueDetails {

    private Long id;

    private String value;

    private Integer valueOrder;

    public DropdownValueDetails(DropdownValue dropdownValue){
        id = dropdownValue.getId();
        value = dropdownValue.getValue();
        valueOrder = dropdownValue.getValueOrder();
    }

}
