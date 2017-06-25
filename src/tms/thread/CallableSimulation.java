package tms.thread;

import tms.model.*;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Created by renan on 6/22/17.
 */
public class CallableSimulation implements Callable {


    private HashMap<Integer, State> states;
    private Tape tape;
    private char initialSymbol;

    public CallableSimulation(Snapshot snapshot) {
        this.states = snapshot.getStates();
        this.tape = snapshot.getTape();
        this.initialSymbol = snapshot.getInitialSymbol();
    }

    @Override
    public Object call() throws Exception {
        State state = states.get(1);
        Transition transition;
        char actualSymbol;
        if(state.getNumberOfTransitions(initialSymbol) == 1){
            transition = state.getTransition(initialSymbol);
        }
        else {
            System.out.println(state.getTransitions(initialSymbol).toString());
            transition = new HaltTransition("ters");
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
                    System.out.println("No transition for state " + state.getId() + " and symbol " + actualSymbol + ".");
                    break;
                }
                System.out.println("...." + state.getId() + ":"+transition.getMessage());
            }
        }

        return "HI!";
    }
}
