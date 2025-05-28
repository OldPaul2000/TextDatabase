package com.textdatabase;

import java.io.*;

public class TextDatabase {

    private String fileLocation;
    private String fileName;
    private boolean idBased;
    private DatabaseStructureType structureType;
    private Class<?> objectType;

    private ObjectManager objectManager;
    private ValueManager valueManager;

    public TextDatabase(DatabaseBuilder builder) {
        this.fileLocation = builder.getFileLocation();
        this.fileName = builder.getFileName();
        this.idBased = builder.isIdBased();
        this.structureType = builder.getStructureType();
        this.objectType = builder.getObjectType();
        this.objectManager = new ObjectManager(this);
        this.valueManager = new ValueManager(this);
    }

    public Object getObject(String propertyName, String value){
        if(structureType == DatabaseStructureType.OBJECT){
            return objectManager.getObject(propertyName, value);
        }
        return null;
    }
    public void setObject(Object object){
        if(structureType == DatabaseStructureType.OBJECT){
            objectManager.setObject(object);
        }
    }

    public void setString(String property, String value){
        if(structureType == DatabaseStructureType.VALUE){
            valueManager.setString(property, value);
        }
    }
    public void setInt(String property, int value){
        if(structureType == DatabaseStructureType.VALUE){
            valueManager.setInt(property, value);
        }
    }
    public void setDouble(String property, double value){
        if(structureType == DatabaseStructureType.VALUE){
            valueManager.setDouble(property, value);
        }
    }

    public String getString(String property){
        if(structureType == DatabaseStructureType.VALUE){
            return valueManager.readString(property);
        }
        return null;
    }
    public Integer getInt(String property){
        if(structureType == DatabaseStructureType.VALUE){
            return valueManager.readInt(property);
        }
        return null;
    }
    public Double getDouble(String property){
        if(structureType == DatabaseStructureType.VALUE){
            return valueManager.readDouble(property);
        }
        return null;
    }


    public void writeText(String text){
        if(structureType == DatabaseStructureType.TEXT){
            try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileLocation + "\\" + fileName, true))){
                bos.write(text.getBytes());
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public String readDatabaseAsText(){
        String fileContent = "";
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileLocation + "\\" + fileName))){
            fileContent = new String(bis.readAllBytes());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return fileContent;
    }

    public void insertNewLine(){
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileLocation + "\\" + fileName, true))){
            bos.write(10);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void clearDatabase(){
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileLocation + "\\" + fileName))){
            bos.write("".getBytes());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseBuilder builder(){
        return new DatabaseBuilder();
    }

    @Override
    public String toString() {
        return "Database info:" + "\n" +
                "fileLocation='" + fileLocation + '\'' + "\n" +
                "fileName='" + fileName + '\'' + "\n" +
                "idBased=" + idBased + "\n" +
                "structureType=" + structureType + "\n" +
                "objectType=" + getObjectTypeName() + "\n";
    }
    private String getObjectTypeName(){
        if(this.objectType == null){
            return null;
        }
        return objectType.getSimpleName();
    }

    public String getFileLocation(){
        return fileLocation;
    }
    public String getFileName(){
        return fileName;
    }
    public Class<?> getObjectType(){
        return objectType;
    }
    public DatabaseStructureType getStructureType(){
        return structureType;
    }
}
