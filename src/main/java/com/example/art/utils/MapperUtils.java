package com.example.art.utils;

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

    public int createEntity(Object entityObj, Object patchObj){
        Class<?> patchClazz = patchObj.getClass();
        int count = 0;
        try {
            List<Field> fields = Arrays.asList(patchClazz.getDeclaredFields());
            for (Field field : fields) {
                field.setAccessible(true);
                if (updateVal(entityObj, field.getName(), field.get(patchObj)))
                    count++;
                field.setAccessible(false);
            }
            log.info("set fields count = {}",count);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    private boolean updateVal(Object obj, String fieldName, Object newVal) {
        Class<?> clazz = obj.getClass();
        if(clazz == null) return false;
        try{
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj,newVal);
            field.setAccessible(false);
            return true;
        }catch (NoSuchFieldException | IllegalAccessException ex){
            log.error(ex.getLocalizedMessage());
        }
        return false;
    }

    public int updateEntity(Object entityObj, Object patchObj, List<String> updateMsgs){

        Class<?> patchClazz = patchObj.getClass();
        int count = 0;
        try {
            List<Field> fields = Arrays.asList(patchClazz.getDeclaredFields());
            for (Field field : fields) {
                field.setAccessible(true);
                if (updateVal(entityObj, field.getName(), field.get(patchObj),updateMsgs))
                    count++;
                field.setAccessible(false);
            }
            log.info("updated fields count = {}",count);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count;

    }

    public int updateResponseFromEntity(Object entity, Object response){
        Class<?> respClazz = response.getClass();
        Class<?> entityClazz = entity.getClass();
        int tcount = 0;
        int count = 0;
        try {
            List<Field> fields = Arrays.asList(respClazz.getDeclaredFields());
            tcount = count = fields.size();
            for (Field field : fields) {
                field.setAccessible(true);
                Field entityField = getEntityField(entityClazz,field.getName());
                if(entityField != null){
                    entityField.setAccessible(true);
                    Object val = entityField.get(entity);
                    field.set(response, val);
                    entityField.setAccessible(false);
                } else {
                    count--;
                }
                field.setAccessible(false);
            }
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        log.info("updated response automatically for {}, {} out of {} fields set.",respClazz.getSimpleName(), count, tcount);
        return count;
    }


    private Field getEntityField(Class<?> clazz, String fieldName) {
        if(clazz == Object.class) return null;
        try {
            Field entityField = clazz.getDeclaredField(fieldName);
            return entityField;
        } catch (NoSuchFieldException e) {
            return getEntityField(clazz.getSuperclass(),fieldName);
        }
    }

    public boolean updateVal(Object obj, String fieldName, Object newVal, List<String> updateMsgs){
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
