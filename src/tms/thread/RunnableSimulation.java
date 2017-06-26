package tms.thread;

import tms.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Created by renan on 6/22/17.
 */
public class RunnableSimulation implements Callable {


    private HashMap<Integer, State> states;
    private Tape tape;
    private char initialSymbol;
    private String computations;
    private int initialState;

    public RunnableSimulation(Snapshot snapshot) {
        this.states = snapshot.getStates();
        this.tape = snapshot.getTape();
        this.initialSymbol = snapshot.getInitialSymbol();
        this.computations = snapshot.getComputations();
        this.initialState = snapshot.getInitialState();
    }

    @Override
    public Object call() throws Exception {
        State state = states.get(initialState);
        Transition transition;
        char actualSymbol = initialSymbol;
        if(state.getNumberOfTransitions(initialSymbol) == 1){
            transition = state.getTransition(initialSymbol);
        }
        else {
            ArrayList transitions = state.getTransitions(actualSymbol);
            return new Snapshot(states, initialState, tape, actualSymbol, computations, transitions);
        }

        while (!transition.isHalt()){
            computations = computations + "...." + state.getId() + ":" + tape.getTape() + "\n";
            tape.write(transition.getWrite());
            tape.move(transition.getMovement());
            actualSymbol = tape.getHeader();
            state = states.get(transition.getNextState());

            if(state.getNumberOfTransitions(actualSymbol) <= 1){ //it is 1 common, or 0 common (only halt)
                transition = state.getTransition(actualSymbol);
                //Verify if the halts, or if there is no transition (not accept)
                if(transition == null){
                    transition = state.getHaltTransition();
                    if(transition == null){
                        System.out.println("No transition for state " + state.getId() + " and symbol " + actualSymbol + ".");
                        break;
                    }
                    computations = computations + ("...." + state.getId() + ":"+transition.getMessage());
                    ArrayList<Transition> halTransitions = new ArrayList<>();
                    halTransitions.add(transition);
                    Snapshot teste = new Snapshot(states, tape, actualSymbol, computations, halTransitions);
                    return teste;
                }
            }

        }

        Snapshot teste = new Snapshot(states, tape, actualSymbol, computations);
        return teste;
    }
}
