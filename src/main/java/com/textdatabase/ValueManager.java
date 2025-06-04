package com.textdatabase;

import java.io.*;

public class ValueManager {

    private TextDatabase database;

    public ValueManager(TextDatabase textDatabase) {
        this.database = textDatabase;
    }

    public void deleteProperty(String property){
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(database.getFileLocation() + "\\" + database.getFileName()))){
            String[] lines = new String(bis.readAllBytes()).split("\n");
            StringBuilder newData = new StringBuilder();
            for(String line:lines){
                if(!line.startsWith(property + "=") || line.isBlank()){
                    newData.append(line).append("\n");
                }
            }
            writeFile(newData.toString(), false);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void setString(String property, String value) {
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(database.getFileLocation() + "\\" + database.getFileName()))){
            writeData(bis, property, value);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void setInt(String property, int value){
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(database.getFileLocation() + "\\" + database.getFileName()))){
            writeData(bis, property, value + "");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void setDouble(String property, double value){
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(database.getFileLocation() + "\\" + database.getFileName()))){
            writeData(bis, property, value + "");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    private void writeData(BufferedInputStream bis, String property, String value) throws IOException{
        boolean propertyFound = false;
        String fileTempContent = new String(bis.readAllBytes());
        String[] lines = fileTempContent.split("\n");
        StringBuilder newData = new StringBuilder();
        for(String line:lines){
            if(!line.startsWith(property + "=")){
                newData.append(line).append("\n");
            }
            else{
                newData.append(property)
                        .append("=")
                        .append(value)
                        .append("\n");
                propertyFound = true;
            }
        }
        if(!propertyFound){
            String newValue = property + "=" + value + "\n";
            writeFile(newValue, true);
        }
        else{
            writeFile(newData.toString(), false);
        }

    }
    private void writeFile(String content, boolean append){
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(database.getFileLocation() + "\\" + database.getFileName(), append))){
            bos.write(content.getBytes());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public String readString(String property){
        String value = "";
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream((database.getFileLocation() + "\\" + database.getFileName())))) {
            String[] lines = new String(bis.readAllBytes()).split("\n");
            for (int i = 0; i < lines.length; i++) {
                String readProperty = lines[i].replaceAll("=.+", "");
                String readValue = lines[i].replaceAll(property + "=", "");
                if (readProperty.equals(property)) {
                    value = readValue;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return value;
    }

    public int readInt(String property){
        int value = 0;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream((database.getFileLocation() + "\\" + database.getFileName())))) {
            String[] lines = new String(bis.readAllBytes()).split("\n");
            for (int i = 0; i < lines.length; i++) {
                String[] propertyAndValue = lines[i].replaceAll("\n", "").split("=");
                if (propertyAndValue[0].equals(property)) {
                    value = Integer.parseInt(propertyAndValue[1]);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return value;
    }

    public double readDouble(String property){
        double value = 0;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream((database.getFileLocation() + "\\" + database.getFileName())))) {
            String[] lines = new String(bis.readAllBytes()).split("\n");
            for (int i = 0; i < lines.length; i++) {
                String[] propertyAndValue = lines[i].replaceAll("\n", "").split("=");
                if (propertyAndValue[0].equals(property)) {
                    value = Double.parseDouble(propertyAndValue[1]);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return value;
    }

}
