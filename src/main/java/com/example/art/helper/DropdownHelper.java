package com.example.art.helper;

import com.example.art.dto.response.DropdownKeyDetails;
import com.example.art.model.enums.DropdownType;
import com.example.art.model.enums.FormType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@Getter
public class DropdownHelper {

    private List<DropdownKeyDetails> dropdownKeyDetailsList;

    private Map<FormType, List<DropdownType>> formDropdownListMap;

    @PostConstruct
    private void listDropdownKeys(){
        dropdownKeyDetailsList = new ArrayList<>();
        for(DropdownType dropdownType : DropdownType.values()){
            dropdownKeyDetailsList.add(
                    new DropdownKeyDetails(dropdownType.name(), dropdownType.getFormType().name(), dropdownType.isStatic()));
        }
    }

    @PostConstruct
    private void createFormDropdownListMap(){
        formDropdownListMap = Arrays.stream(DropdownType.values()).collect(Collectors.groupingBy(
                DropdownType::getFormType ,
                Collectors.mapping( dropdownType -> dropdownType, Collectors.toList())
        ));
        log.info(String.valueOf(formDropdownListMap));
    }

    public DropdownType getDropdownType(String key) {
        if(key == null) return null;
        try{
            return DropdownType.valueOf(key);
        }
        catch (IllegalArgumentException e){
            log.error("error parsing dropdown type, {}",e.getMessage());
            return null;
        }
    }

    public FormType getFormType(String key){
        if(key == null) return null;
        try{
            return FormType.valueOf(key);
        }
        catch (IllegalArgumentException e){
            log.error("error parsing form type, {}",e.getMessage());
            return null;
        }
    }

    public List<DropdownType> getDropdownTypeList(FormType formType){
        return formDropdownListMap.get(formType);
    }

}
