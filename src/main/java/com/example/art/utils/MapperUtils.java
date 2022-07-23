package com.example.art.utils;

import com.example.art.dto.request.UpdateProductDetailsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class MapperUtils {

    @Autowired
    private StringUtils stringUtils;

    public int updateEntity(Object entityObj, Object patchObj, List<String> updateMsgs){

        Class<?> patchClazz = patchObj.getClass();
        int count = 0;
        try {
            List<Field> fields = Arrays.asList(patchClazz.getDeclaredFields());
            for (Field field : fields) {
                field.setAccessible(true);
                if (setVal(entityObj, field.getName(), field.get(patchObj),updateMsgs))
                    count++;
                field.setAccessible(false);
            }
            log.info("updated fields count = {}",count);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count;

    }

    public boolean setVal(Object obj, String fieldName, Object newVal, List<String> updateMsgs){
        Class<?> clazz = obj.getClass();
        if(clazz == null) return false;
        try{
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object oldVal = field.get(obj);
            if(stringUtils.isUpdateable(oldVal, newVal)){
                field.set(obj,newVal);
                updateMsgs.add(stringUtils.generateUpdateMsg(fieldName,oldVal,newVal));
                field.setAccessible(false);
                return true;
            }
            field.setAccessible(false);
            return false;
        }catch (NoSuchFieldException | IllegalAccessException ex){
            log.error(ex.getLocalizedMessage());
        }
        return false;
    }

}
