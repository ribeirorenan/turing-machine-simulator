package tms.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by renan on 6/24/17.
 */


public class Snapshot {
    private HashMap<Integer, State> states;
    private Tape tape;
    private int initialState;
    private char initialSymbol;
    private String computations;
    private int numberOfComputations;
    private ArrayList<Transition> nondeterministicTransitions;

    public Snapshot(HashMap<Integer, State> states, Tape tape, char initialSymbol) {
        this.states = states;
        this.tape = tape;
        this.initialSymbol = initialSymbol;
        this.computations = "";
        this.nondeterministicTransitions = new ArrayList<>();
        this.initialState = 1;
    }
    public Snapshot(HashMap<Integer, State> states, Tape tape, char initialSymbol, int state) {
        this.states = states;
        this.tape = tape;
        this.initialSymbol = initialSymbol;
        this.computations = "";
        this.nondeterministicTransitions = new ArrayList<>();
        this.initialState = state;
    }

    public Snapshot(HashMap<Integer, State> states, Tape tape, char initialSymbol, String computations) {
        this.states = states;
        this.tape = tape;
        this.initialSymbol = initialSymbol;
        this.computations = computations;
        this.nondeterministicTransitions = new ArrayList<>();
        this.initialState = 1;
    }

    public Snapshot(HashMap<Integer, State> states, Tape tape, char initialSymbol, String computations, ArrayList<Transition> nondeterministicTransitions) {
        this.states = states;
        this.tape = tape;
        this.initialSymbol = initialSymbol;
        this.computations = computations;
        this.nondeterministicTransitions = nondeterministicTransitions;
        this.initialState = 1;
    }

    public Snapshot(HashMap<Integer, State> states, int initialState, Tape tape, char initialSymbol, String computations, ArrayList<Transition> nondeterministicTransitions) {
        this.states = states;
        this.initialState = initialState;
        this.tape = tape;
        this.initialSymbol = initialSymbol;
        this.computations = computations;
        this.nondeterministicTransitions = nondeterministicTransitions;
    }



    public int getNumberOfComputations() {
        return numberOfComputations;
    }

    public void setNumberOfComputations(int numberOfComputations) {
        this.numberOfComputations = numberOfComputations;
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

    public ArrayList<Transition> getNondeterministicTransitions() {
        return nondeterministicTransitions;
    }

    public void setNondeterministicTransitions(ArrayList<Transition> nondeterministicTransitions) {
        this.nondeterministicTransitions = nondeterministicTransitions;
    }

    public int getInitialState() {
        return initialState;
    }

    public void setInitialState(int initialState) {
        this.initialState = initialState;
    }
}
