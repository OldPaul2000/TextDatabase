package com.textdatabase;

public class Main {
    public static void main(String[] args) {

        TextDatabase database = TextDatabase.builder()
                .fileLocation("C:\\Users\\paulb\\Desktop")
                .fileName("ValuesDatabase.txt")
                .structureType(DatabaseStructureType.VALUE)
                .objectType(Car.class)
                .build();



    }

}
