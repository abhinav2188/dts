package com.example.art.services.impl;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.request.DropdownValueRequest;
import com.example.art.dto.response.*;
import com.example.art.dto.response.inner.DropdownKeyValuesDetails;
import com.example.art.dto.response.inner.DropdownValueDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.MissingUserRequestParamException;
import com.example.art.helper.DropdownHelper;
import com.example.art.model.DropdownValue;
import com.example.art.model.enums.DropdownType;
import com.example.art.model.enums.FormType;
import com.example.art.repository.DropdownRepository;
import com.example.art.services.DropdownService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class DropdownServiceImpl implements DropdownService {

    @Autowired
    private DropdownRepository dropdownRepository;

    @Autowired
    private DropdownHelper dropdownHelper;

    @Autowired
    private DerivedDropdownService derivedDropdownService;

    @Override
    public BaseResponse addDropdownValue(DropdownValueRequest dto) throws EntityNotFoundException {

        DropdownType dropdownType = dropdownHelper.getDropdownType(dto.getKey());
        if(dropdownType == null){
            throw new EntityNotFoundException("Dropdown","dropdownType",dto.getKey());
        }

        DropdownValue entity = getDropdownValueEntity(dto, dropdownType);

        entity = dropdownRepository.save(entity);

        String msg = "value '" + entity.getValue() + "' added to dropdown '" + entity.getDropdownType().name() + "'";

        return BaseResponse.builder()
                .status(HttpStatus.OK)
                .responseMsg(msg)
                .data(entity)
                .build();

    }

    @Override
    public BaseResponse<DropdownKeysResponse> getAllDropdownKeys() {

        DropdownKeysResponse response = new DropdownKeysResponse();
        response.setKeys(dropdownHelper.getDropdownKeyDetailsList());

        return BaseResponse.<DropdownKeysResponse>builder()
                .status(HttpStatus.OK)
                .data(response)
                .build();
    }

    @Override
    public BaseResponse<DropdownKeyValuesResponse> getAllDropdownValues() {

        DropdownKeyValuesResponse responseData = new DropdownKeyValuesResponse();

        Map<String, DropdownKeyValuesDetails> dropdownKeyDetailsMap = getKeyValuesDetailsMap(null);

        // todo : for static, dropdowns dealing with other entities like contact, user etc.

        responseData.setDropdownKeyDetailsMap(dropdownKeyDetailsMap);

        return BaseResponse.<DropdownKeyValuesResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg("returned "+dropdownKeyDetailsMap.size()+" dropdown key, values")
                .data(responseData)
                .build();
    }

    @Override
    public BaseResponse deleteDropdownValue(Long valueId) {
        try{
            dropdownRepository.deleteById(valueId);
            return BaseResponse.builder()
                    .status(HttpStatus.OK)
                    .responseMsg("dropdown value deleted")
                    .build();
        }
        catch (EmptyResultDataAccessException e){
            log.error("error deleting dropdown value with id={}, error: {}",valueId,e.getMessage());
            return BaseResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .responseMsg("error deleting dropdown value, value doesn't exists")
                    .build();
        }
    }

    @Override
    public BaseResponse<DropdownKeyValuesResponse> getDropdownValues(String dropdownType, String formType) throws MissingUserRequestParamException {

        // return details of a particular dropdown or dropdowns belonging to particular form_type

        DropdownType dropdownType1 = dropdownHelper.getDropdownType(dropdownType);
        FormType formType1 = dropdownHelper.getFormType(formType);

        // if both dropdownType and formType are present formType is given priority
        if(formType1 != null){
            return getFormDropdownValues(formType1);
        }
        else if(dropdownType1 != null){
            return getSingleDropdownValues(dropdownType1);
        }
        else{
            throw new MissingUserRequestParamException("either of dropdownType or formType request parameters should contain valid value");
        }

    }

    private BaseResponse<DropdownKeyValuesResponse> getSingleDropdownValues(DropdownType dropdownType) {

        DropdownKeyValuesResponse response = new DropdownKeyValuesResponse();
        Map<String, DropdownKeyValuesDetails> map = new HashMap<>();

        map.put(dropdownType.name(), getDropdownKeyValueDetails(dropdownType));

        response.setDropdownKeyDetailsMap(map);

        return BaseResponse.<DropdownKeyValuesResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg("returned '"+dropdownType.name()+"' dropdown values")
                .data(response)
                .build();
    }

    private BaseResponse<DropdownKeyValuesResponse> getFormDropdownValues(FormType formType1) {

        DropdownKeyValuesResponse response = new DropdownKeyValuesResponse();
        List<DropdownType> dropdownTypes = dropdownHelper.getDropdownTypeList(formType1);

        response.setDropdownKeyDetailsMap(getKeyValuesDetailsMap(dropdownTypes));

        return BaseResponse.<DropdownKeyValuesResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg("returned "+response.getDropdownKeyDetailsMap().size()+" dropdown key, values")
                .data(response)
                .build();
    }

    private Map<String, DropdownKeyValuesDetails> getKeyValuesDetailsMap(List<DropdownType> dropdownTypes){

        // returns the values of all the dropdowns from database

        // we return all direct dropdown values if dropdownTypes is null
        // direct means fetched from DropdownValue table

        List<DropdownValue> dropdownValues;
        if(dropdownTypes == null){
            dropdownValues = dropdownRepository.findByOrderByValueOrderAsc();
        }
        else{
            dropdownValues = dropdownRepository.findByDropdownTypeInOrderByValueOrderAsc(dropdownTypes);
        }

        log.info("dropdown values : {}",dropdownValues);
        Map<DropdownType, List<DropdownValueDetails>> map1 =
        dropdownValues.stream().collect(
                Collectors.groupingBy(
                        DropdownValue::getDropdownType,
                        Collectors.mapping( dropdownValue -> new DropdownValueDetails(dropdownValue), Collectors.toList())
                ));

        Map<String, DropdownKeyValuesDetails> keyValuesMap = new HashMap<>();

        for(DropdownType dropdownType : dropdownTypes){
            if(dropdownType.isDerived()){
                DropdownKeyValuesDetails details = derivedDropdownService.getDerivedDropdownDetails(dropdownType);
                keyValuesMap.put(dropdownType.name(), details);
            }
            else{
                DropdownKeyValuesDetails details = new DropdownKeyValuesDetails();
                details.setDropdownKey(dropdownType.name());
                details.setFormName(dropdownType.getFormType().name());
                details.setValues(map1.getOrDefault(dropdownType,new ArrayList<>()));
                keyValuesMap.put(dropdownType.name(),details);
            }
        }

        return keyValuesMap;
    }


    private DropdownKeyValuesDetails getDropdownKeyValueDetails(DropdownType dropdownType) {

        // returns detail related to a single dropdown

        DropdownKeyValuesDetails dropdownKeyValuesDetails = new DropdownKeyValuesDetails();
        dropdownKeyValuesDetails.setDropdownKey(dropdownType.name());
        dropdownKeyValuesDetails.setFormName(dropdownType.getFormType().name());

        List<DropdownValueDetails> dropdownValueDetails = dropdownRepository.findByDropdownTypeOrderByValueOrderAsc(dropdownType);
//        if(dropdownValueDetails.size() == 0) return null;

        dropdownKeyValuesDetails.setValues(dropdownValueDetails);

        return dropdownKeyValuesDetails;

    }


    private DropdownValue getDropdownValueEntity(DropdownValueRequest dto, DropdownType dropdownType) {
        DropdownValue entity = new DropdownValue();
        entity.setDropdownType(dropdownType);
        entity.setValue(dto.getValue());
        entity.setValueOrder(dto.getOrder());
        return entity;
    }

}
