package com.textdatabase;

public class Main {
    public static void main(String[] args) {


        TextDatabase valuesDb = TextDatabase.builder()
                .fileLocation("C:\\Users\\paulb\\Desktop")
                .fileName("ValuesDatabase.txt")
                .structureType(DatabaseStructureType.VALUE)
                .build();


        valuesDb.deleteValue("value34");

    }

}