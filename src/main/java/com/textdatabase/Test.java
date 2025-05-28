package com.textdatabase;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream("C:\\Users\\paulb\\Desktop\\1000CarObjects.txt"))){
            String content = new String(bis.readAllBytes());
            System.out.println(content);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }



    }

}
