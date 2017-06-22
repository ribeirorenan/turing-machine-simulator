package tms.model;

import tms.controller.FileHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by renan on 6/5/17.
 */
public class TuringMachine {

    private HashMap<Integer, State> states;
    private Tape tape;

    public TuringMachine(String fita){
        this.states = new HashMap<>();
        this.tape = new Tape(fita);
    }

    public void loadFile(){
        FileHandler fileHandler = new FileHandler("ab.mt");

        try {
            fileHandler.loadMachine(states);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run(){

    }

    public HashMap<Integer, State> getStates() {
        return states;
    }

    public void setStates(HashMap<Integer, State> states) {
        this.states = states;
    }

    public Tape getTape() {
        return tape;
    }

    public void setTape(Tape tape) {
        this.tape = tape;
    }
}
