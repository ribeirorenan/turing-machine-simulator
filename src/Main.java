import tms.controller.FileHandler;
import tms.model.TuringMachine;

import java.io.IOException;

/**
 * Created by renan on 6/5/17.
 */
public class Main {
    public static void main(String[] args) {

        TuringMachine turingMachine = new TuringMachine();

        turingMachine.simulate();

        System.out.println(turingMachine.getStates().toString());

    }
}
