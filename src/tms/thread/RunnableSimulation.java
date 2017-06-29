package tms.thread;

import tms.controller.TuringMachine;
import tms.model.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by renan on 6/22/17.
 */
public class RunnableSimulation implements Runnable {


    private int id;
    private HashMap<Integer, State> states;
    private Tape tape;
    private char initialSymbol;
    private String computations;
    private int initialState;
    private boolean running;
    TuringMachine turingMachine;
    Snapshot snapshot;

    public RunnableSimulation(int id, Snapshot snapshot, TuringMachine turingMachine) {
        this.id = id;
        this.snapshot = snapshot;
        this.states = this.snapshot.getStates();
        this.tape = this.snapshot.getTape();
        this.initialSymbol = this.snapshot.getInitialSymbol();
        this.computations = this.snapshot.getComputations();
        this.initialState = this.snapshot.getInitialState();
        this.turingMachine = turingMachine;
    }

    @Override
    public void run() {
        this.running = true;

        State state = states.get(initialState);
        Transition transition;
        char actualSymbol = initialSymbol;
        if(state.getNumberOfTransitions(initialSymbol) == 1){
            transition = state.getTransition(initialSymbol);
        }
        else if(state.getNumberOfTransitions(initialSymbol) > 1){
            ArrayList transitions = state.getTransitions(actualSymbol);
            Tape newTape = tape.getNewTape();
            Snapshot nondeterministicSnapshot = new Snapshot(states, state.getId(), newTape, initialSymbol, computations, transitions);
            turingMachine.handleNonDeterministicTransitions(nondeterministicSnapshot);
            try {
                this.running = false;
                this.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            transition = new HaltTransition("end");
        }else{
            transition = new HaltTransition("No transition for it");
            computations = computations + "..." + state.getId() + ":" + tape.getTape() + "\n";
            computations = computations + ("No transition for state " + state.getId() + " and symbol " + actualSymbol + ".");
            snapshot.setComputations(computations);
            turingMachine.notAccepted(snapshot);
        }

        while (!transition.isHalt()){
            computations = computations + "..." + state.getId() + ":" + tape.getTape() + "\n";
            tape.write(transition.getWrite());
            tape.move(transition.getMovement());
            actualSymbol = tape.getHeader();
            state = states.get(transition.getNextState());
            if(state.getNumberOfTransitions(actualSymbol) <= 1 || state.getNumberOfTransitions(actualSymbol) == 1){ //it is 1 common, or 0 common (only halt)
                transition = state.getTransition(actualSymbol);
                //Verify if the halts, or if there is no transition (not accept)
                if(transition == null){
                    transition = state.getHaltTransition();
                    if(transition == null){
                        computations = computations + "..." + state.getId() + ":" + tape.getTape() + "\n";
                        computations = computations + ("No transition for state " + state.getId() + " and symbol " + actualSymbol + ".");
                        snapshot.setComputations(computations);
                        turingMachine.notAccepted(snapshot);
                        break;
                    }
                    computations = computations + "..." + state.getId() + ":" + tape.getTape() + "\n";
                    computations = computations + ("..." + state.getId() + ":"+transition.getMessage());
                    ArrayList<Transition> halTransitions = new ArrayList<>();
                    halTransitions.add(transition);
                    Snapshot acceptedSnapshot = new Snapshot(states, tape, actualSymbol, computations, halTransitions);
                    turingMachine.accepted(acceptedSnapshot);
                }
            }
            else {
                ArrayList transitions = state.getTransitions(actualSymbol);
                Tape newTape = tape.getNewTape();
                Snapshot nondeterministicSnapshot = new Snapshot(states, state.getId(), newTape, actualSymbol, computations, transitions);
                turingMachine.handleNonDeterministicTransitions(nondeterministicSnapshot);
                transition = new HaltTransition("Thread " + id + " finalized");
                computations = computations + ("..." + state.getId() + ":"+transition.getMessage());
                nondeterministicSnapshot.setComputations(computations);
                turingMachine.notAccepted(nondeterministicSnapshot);
            }

        }
        this.running = false;
//        turingMachine.tryToKillExecutor();
    }



    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
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

    public char getInitialSymbol() {
        return initialSymbol;
    }

    public void setInitialSymbol(char initialSymbol) {
        this.initialSymbol = initialSymbol;
    }

    public String getComputations() {
        return computations;
    }

    public void setComputations(String computations) {
        this.computations = computations;
    }

    public int getInitialState() {
        return initialState;
    }

    public void setInitialState(int initialState) {
        this.initialState = initialState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSnapshot(Snapshot snapshot){
        this.snapshot = snapshot;
        this.states = snapshot.getStates();
        this.tape = snapshot.getTape();
        this.initialSymbol = snapshot.getInitialSymbol();
        this.computations = snapshot.getComputations();
        this.initialState = snapshot.getInitialState();
    }

}
