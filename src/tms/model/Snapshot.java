package tms.model;

import java.util.HashMap;

/**
 * Created by renan on 6/24/17.
 */
public class Snapshot {
    private HashMap<Integer, State> states;
    private Tape tape;
    private char initialSymbol;
    private String computations;

    public Snapshot(HashMap<Integer, State> states, Tape tape, char initialSymbol) {
        this.states = states;
        this.tape = tape;
        this.initialSymbol = initialSymbol;
        this.computations = "";
    }

    public Snapshot(HashMap<Integer, State> states, Tape tape, char initialSymbol, String computations) {
        this.states = states;
        this.tape = tape;
        this.initialSymbol = initialSymbol;
        this.computations = computations;
    }

    public HashMap<Integer, State> getStates() {
        return states;
    }

    public String getComputations() {
        return computations;
    }

    public void setComputations(String computations) {
        this.computations = computations;
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

    public char getInitialSymbol() {
        return initialSymbol;
    }

    public void setInitialSymbol(char initialSymbol) {
        this.initialSymbol = initialSymbol;
    }
}
