package com.example.art.exceptions;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DuplicateEntryException extends BusinessException{

    public DuplicateEntryException(List<String> fields){
        String msg = "Fields "+fields.toString()+" are duplicate, need to be unique";
        Map<String, String> errors = fields.stream().collect(Collectors.toMap(f->f, f->"Duplicate entry"));
        this.setErrors(errors);
        this.setCustomMsg(msg);
    }

    public DuplicateEntryException(String field){
        super("Field "+field+" is duplicate, need to be unique", Collections.singletonMap("field","Duplicate entry"));
    }

}
