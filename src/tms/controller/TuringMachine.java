package tms.controller;

import tms.model.State;
import tms.model.Tape;
import tms.model.Transition;
import tms.thread.RunnableSimulation;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by renan on 6/5/17.
 */
public class TuringMachine{

    private HashMap<Integer, State> states;
    private Tape tape;
    private char initialSymbol;

    public TuringMachine(String tape){
        this.states = new HashMap<>();
        this.tape = new Tape(tape);
        initialSymbol = this.tape.getHeader();
    }

    public void loadFile(){
        FileHandler fileHandler = new FileHandler("ab.mt");

        try {
            fileHandler.loadMachine(states);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void simulate(){
        Thread mt = new Thread(new RunnableSimulation(states, tape, initialSymbol));
        mt.run();
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
