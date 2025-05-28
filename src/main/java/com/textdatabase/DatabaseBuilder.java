package com.textdatabase;

public class DatabaseBuilder {

    private String fileLocation;
    private String fileName;
    private boolean idBased = false;
    private DatabaseStructureType structureType;
    private Class<?> objectType;

    public String getFileLocation(){
        return this.fileLocation;
    }

    public DatabaseBuilder fileLocation(String fileLocation){
        this.fileLocation = fileLocation;
        return this;
    }

    public String getFileName(){
        return this.fileName;
    }

    public DatabaseBuilder fileName(String fileName){
        this.fileName = fileName;
        return this;
    }

    public boolean isIdBased(){
        return this.idBased;
    }

    public DatabaseBuilder idBased(boolean idBased){
        this.idBased = idBased;
        return this;
    }

    public DatabaseStructureType getStructureType(){
        return this.structureType;
    }

    public DatabaseBuilder structureType(DatabaseStructureType structureType){
        this.structureType = structureType;
        return this;
    }

    public Class<?> getObjectType(){
        return this.objectType;
    }

    public DatabaseBuilder objectType(Class<?> type){
        this.objectType = type;
        return this;
    }

    public TextDatabase build() {
        if(structureType == DatabaseStructureType.TEXT || structureType == DatabaseStructureType.VALUE){
            this.idBased = false;
            this.objectType = null;
        }
        return new TextDatabase(this);
    }

}
