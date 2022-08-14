package com.example.art.dto.mapper;

import com.example.art.dto.response.inner.DropdownKeyValuesDetails;
import com.example.art.dto.response.inner.DropdownValueDetails;
import com.example.art.model.enums.DropdownType;
import com.example.art.model.views.BrochureDropdownView;
import com.example.art.model.views.ContactDropdownView;
import com.example.art.model.views.PartyDropdownView;
import com.example.art.model.views.UserHandlerDropdownView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DropdownMapper {

    public DropdownKeyValuesDetails getPartyDropdownKeyValuesDetails(DropdownType dropdownType, List<PartyDropdownView> viewList) {
        DropdownKeyValuesDetails details = new DropdownKeyValuesDetails();
        details.setDropdownKey(dropdownType.name());
        details.setFormName(dropdownType.getFormType().name());
        List<DropdownValueDetails> valueDetails = viewList.stream()
                .map(this::getDropdownValueDetails)
                .collect(Collectors.toList());
        details.setValues(valueDetails);
        return details;
    }

    private DropdownValueDetails getDropdownValueDetails(PartyDropdownView partyDropdownView) {
        DropdownValueDetails details = new DropdownValueDetails();
        details.setValue(partyDropdownView.getPartyName());
        details.setId(partyDropdownView.getId());
        return details;
    }

    public DropdownKeyValuesDetails getHandlerDropdownKeyValuesDetails(DropdownType dropdownType, List<UserHandlerDropdownView> viewList) {
        DropdownKeyValuesDetails details = new DropdownKeyValuesDetails();
        details.setDropdownKey(dropdownType.name());
        details.setFormName(dropdownType.getFormType().name());
        List<DropdownValueDetails> valueDetails = viewList.stream()
                .map(this::getDropdownValueDetails)
                .collect(Collectors.toList());
        details.setValues(valueDetails);
        return details;
    }

    private DropdownValueDetails getDropdownValueDetails(UserHandlerDropdownView view) {
        DropdownValueDetails details = new DropdownValueDetails();
        details.setValue(view.getEmail());
        details.setId(view.getId());
        return details;
    }

    public DropdownKeyValuesDetails getContactDropdownKeyValuesDetails(DropdownType dropdownType, List<ContactDropdownView> viewList) {
        DropdownKeyValuesDetails details = new DropdownKeyValuesDetails();
        details.setDropdownKey(dropdownType.name());
        details.setFormName(dropdownType.getFormType().name());
        List<DropdownValueDetails> valueDetails = viewList.stream()
                .map(this::getDropdownValueDetails)
                .collect(Collectors.toList());
        details.setValues(valueDetails);
        return details;
    }

    private DropdownValueDetails getDropdownValueDetails(ContactDropdownView view) {
        DropdownValueDetails details = new DropdownValueDetails();
        details.setValue(view.getEmail());
        details.setId(view.getId());
        return details;
    }

    public DropdownKeyValuesDetails getBrochureDropdownKeyValuesDetails(DropdownType dropdownType, List<BrochureDropdownView> viewList) {
        DropdownKeyValuesDetails details = new DropdownKeyValuesDetails();
        details.setDropdownKey(dropdownType.name());
        details.setFormName(dropdownType.getFormType().name());
        List<DropdownValueDetails> valueDetails = viewList.stream()
                .map(this::getDropdownValueDetails)
                .collect(Collectors.toList());
        details.setValues(valueDetails);
        return details;
    }

    private DropdownValueDetails getDropdownValueDetails(BrochureDropdownView brochureDropdownView) {
        DropdownValueDetails details = new DropdownValueDetails();
        details.setValue(brochureDropdownView.getBrochureName());
        details.setId(brochureDropdownView.getId());
        return details;
    }

}
