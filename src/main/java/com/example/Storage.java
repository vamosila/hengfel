/*
* File: Storage.java
* Author: Vámosi László Ádám
* Copyright: 2025, Vámosi László Ádám
* Group: Szoft I-N
* Date: 2025-05-20
* GitHub: https://github.com/vamosilaszloadam/
* Licenc: MIT
*/

package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static String _fileName="";
    private static Boolean _fileReadSuccessful=false;
    private static Boolean _fileWriteSuccessful=false;
    public static String getFileName() {
        return _fileName;
    }
    public static void setFileName(String fileName) {
        _fileName = fileName;
    }
    public static Boolean getFileReadSuccessful() {
        return _fileReadSuccessful;
    }
    public static void setFileReadSuccessful(Boolean fileReadSuccessful) {
        _fileReadSuccessful = fileReadSuccessful;
    }
    public static Boolean getFileWriteSuccessful() {
        return _fileWriteSuccessful;
    }
    public static void setFileWriteSuccessful(Boolean fileWriteSuccessful) {
        _fileWriteSuccessful = fileWriteSuccessful;
    }

    public static void writeFile(String calcLine){
        try {
            tryWriteFile(calcLine);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            _fileWriteSuccessful=false;
        }
    }
    private static void tryWriteFile(String calcLine) throws IOException{
        try(FileWriter fw=new FileWriter(_fileName,true)){
            fw.write(calcLine+"\n");
        }
        _fileWriteSuccessful=true;
    }
    public static void writeFile(ArrayList<CylinderCalculation> calcList){
        try {
            tryWriteFile(calcList);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            _fileWriteSuccessful=false;
        }
    }
    private static void tryWriteFile(ArrayList<CylinderCalculation> calcList) throws IOException{
        try(FileWriter fw=new FileWriter(_fileName)){
            for(CylinderCalculation calc:calcList){
                fw.write(calc.getRadius()+":"+calc.getHeight()+":"+calc.getSurface()+"\n");
            }
        }
        _fileWriteSuccessful=true;
    }
    public static ArrayList<CylinderCalculation> readFile(){
        try {
            return tryReadFile();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            _fileReadSuccessful=false;
            return new ArrayList<>();
        }
    }
    private static ArrayList<CylinderCalculation> tryReadFile() throws FileNotFoundException{
        ArrayList<CylinderCalculation> cylinderList=new ArrayList<>();
        File file=new File(_fileName);
        try(Scanner sc=new Scanner(file)){
            while(sc.hasNextLine()){
                String line=sc.nextLine();
                System.out.println(line);
                String[] row=line.split(":");
                CylinderCalculation cylinder=new CylinderCalculation();
                cylinder.setRadius(Double.parseDouble(row[0]));
                cylinder.setHeight(Double.parseDouble(row[1]));
                cylinder.setSurface(Double.parseDouble(row[2]));
                cylinderList.add(cylinder);
            }
        }
        System.out.println("\nA(z) \""+_fileName+"\" nevű fájl beolvasása sikeres.\n");
        _fileReadSuccessful=true;
        return cylinderList;
    }
}
