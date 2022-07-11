package com.example.art.controllers.internal;

import com.example.art.dto.BaseResponse;
import com.example.art.dto.request.DropdownValueRequest;
import com.example.art.services.DropdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/int/dropdown")
public class DropdownIntController {

    @Autowired
    private DropdownService dropdownService;

    @PostMapping
    public BaseResponse addDropdownValue(@RequestBody DropdownValueRequest dto){
        return dropdownService.addDropdownValue(dto);
    }

    @GetMapping("/keys")
    public BaseResponse getALlDropdownKeys(){
        return dropdownService.getAllDropdownKeys();
    }

    @GetMapping("/values/all")
    public BaseResponse getAllDropdownValues(){
        return dropdownService.getAllDropdownValues();
    }

    @DeleteMapping("/value")
    public BaseResponse deleteDropdownValue(@RequestParam(name = "valueId") Long valueId){
        return dropdownService.deleteDropdownValue(valueId);
    }
}
