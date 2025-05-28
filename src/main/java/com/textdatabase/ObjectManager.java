package com.textdatabase;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectManager {

    private TextDatabase database;

    public ObjectManager(TextDatabase textDatabase) {
        this.database = textDatabase;
    }

    public Object getObject(String propertyName, String value){
        Object object = null;
        try{
            String[] propertiesAndValues = extractTextObject(propertyName, value);
            if(propertiesAndValues == null){
                return null;
            }
            String[] values = new String[propertiesAndValues.length];
            for(int i = 0; i < propertiesAndValues.length; i++){
                values[i] = propertiesAndValues[i].replaceAll(".+=","");
            }
            Field[] classFields = database.getObjectType().getDeclaredFields();
            Class<?>[] fieldsTypes = new Class<?>[classFields.length];
            for(int i = 0; i < classFields.length; i++){
                fieldsTypes[i] = classFields[i].getType();
            }
            Object[] inputValues = new Object[classFields.length];
            for(int i = 0; i < classFields.length; i++){
                if(classFields[i].getType().getSimpleName().equals("String")){
                    inputValues[i] = values[i];
                }
                if(classFields[i].getType().getSimpleName().equals("int")){
                    inputValues[i] = Integer.parseInt(values[i]);
                }
                if(classFields[i].getType().getSimpleName().equals("double")){
                    inputValues[i] = Double.parseDouble(values[i]);
                }
            }
            object = database
                    .getObjectType()
                    .getDeclaredConstructor(fieldsTypes)
                    .newInstance(inputValues);
        }
        catch (NoSuchMethodException |
               InstantiationException |
               InvocationTargetException |
               IllegalAccessException e){
            System.out.println("Error creating object");
        }
        return object;
    }
    private String[] extractTextObject(String propertyName, String value){
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(database.getFileLocation() + "\\" + database.getFileName()))){
            String[] lines = new String(bis.readAllBytes()).split("\n");
            for(String line:lines){
                String[] properties = line.replaceAll(".*\\{(.+)}","$1").split("; ");
                for(String property: properties){
                    if(property.equals(propertyName + "=" + value)){
                        return properties;
                    }
                }
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void setObject(Object object){
        if(database.getObjectType().isInstance(object)){
            List<List<String>> objectProperties = getObjectValues(object);
            try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(database.getFileLocation() + "\\" + database.getFileName(), true))){
                List<String> fields = objectProperties.get(0);
                List<String> values = objectProperties.get(1);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(database.getObjectType().getSimpleName() + "{");
                for(int i = 0; i < fields.size(); i++){
                    if(i < fields.size() - 1){
                        stringBuilder.append(fields.get(i) + "=" + values.get(i) + "; ");
                    }
                    else{
                        stringBuilder.append(fields.get(i) + "=" + values.get(i) + "}\n");
                    }
                }
                bos.write(stringBuilder.toString().getBytes());
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
    private List<List<String>> getObjectValues(Object object){
        List<String> fieldsNames = new ArrayList<>();
        List<String> fieldsValues = new ArrayList<>();
        List<List<String>> fieldsAndValues = new ArrayList<>();
        fieldsAndValues.add(fieldsNames);
        fieldsAndValues.add(fieldsValues);

        Field[] fields = database.getObjectType().getDeclaredFields();
        for(Field field: fields){
            String fieldNameFirstLetterCapitalized = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
            String methodName = "get" + fieldNameFirstLetterCapitalized;
            try{
                Method getMethod = database.getObjectType().getDeclaredMethod(methodName);
                String value = getMethod.invoke(object).toString();
                fieldsNames.add(field.getName());
                fieldsValues.add(value);
            }
            catch (NoSuchMethodException | IllegalAccessException |InvocationTargetException e){
                System.out.println(e.getMessage());
            }
        }
        return fieldsAndValues;
    }

}
