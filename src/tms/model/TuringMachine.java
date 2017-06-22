package tms.model;

import tms.controller.FileHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by renan on 6/5/17.
 */
public class TuringMachine {

    private HashMap<Integer, State> states;

    public TuringMachine(){
        this.states = new HashMap<>();
    }

    public void simulate(){

        FileHandler fileHandler = new FileHandler("ab.mt");

        try {
            fileHandler.tokenizerFile(states);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public HashMap<Integer, State> getStates() {
        return states;
    }

    public void setStates(HashMap<Integer, State> states) {
        this.states = states;
    }
}
