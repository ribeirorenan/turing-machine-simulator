package tms.thread;

import tms.model.HaltTransition;
import tms.model.State;
import tms.model.Tape;
import tms.model.Transition;

import java.util.HashMap;

/**
 * Created by renan on 6/22/17.
 */
public class RunnableSimulation implements Runnable {


    private HashMap<Integer, State> states;
    private Tape tape;
    private char initialSymbol;

    public RunnableSimulation(HashMap<Integer, State> states, Tape tape, char initialSymbol) {
        this.states = states;
        this.tape = tape;
        this.initialSymbol = initialSymbol;
    }

    @Override
    public void run() {
        State state = states.get(1);
        Transition transition;
        char actualSymbol = initialSymbol;
        if(state.getNumberOfTransitions(initialSymbol) == 1){
            transition = state.getTransition(initialSymbol);
        }
        else {
            transition = new HaltTransition("end");
        }

        while (!transition.isHalt()){
            System.out.println("...." + state.getId() + ":" + tape.getTape());
            tape.write(transition.getWrite());
            tape.move(transition.getMovement());
            actualSymbol = tape.getHeader();
            state = states.get(transition.getNextState());
            transition = state.getTransition(actualSymbol);
            if(transition == null){
                transition = state.getHaltTransition();
                if(transition == null){
                    System.out.println("No transition for state " + state.getId() + " and symbol " + actualSymbol);
                    break;
                }
                System.out.println("...." + state.getId() + ":"+transition.getMessage());
            }
        }

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
}
