package com.example.art.helper;

import com.example.art.dto.response.DropdownKeyDetails;
import com.example.art.model.enums.DropdownType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Getter
public class DropdownHelper {

    private List<DropdownKeyDetails> dropdownKeyDetailsList;

    @PostConstruct
    private void listDropdownKeys(){
        dropdownKeyDetailsList = new ArrayList<>();
        for(DropdownType dropdownType : DropdownType.values()){
            dropdownKeyDetailsList.add(
                    new DropdownKeyDetails(dropdownType.name(), dropdownType.getFormType().name(), dropdownType.isStatic()));
        }
    }


    public DropdownType getDropdownType(String key) {
        try{
            return DropdownType.valueOf(key);
        }
        catch (IllegalArgumentException e){
            log.error("error parsing dropdown type, {}",e.getMessage());
            return null;
        }
    }

}
