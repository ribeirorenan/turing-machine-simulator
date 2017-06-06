package tms.model;

import tms.controller.FileHandler;

import java.io.IOException;

/**
 * Created by renan on 6/5/17.
 */
public class TuringMachine {


    public void simulate(){

        FileHandler fileHandler = new FileHandler("ab.mt");

        try {
            fileHandler.printFile();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }



}
