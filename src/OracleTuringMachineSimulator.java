import tms.controller.TuringMachine;
import tms.view.Console;

/**
 * Created by renan on 6/5/17.
 */
public class OracleTuringMachineSimulator {
    public static void main(String[] args) {

        Console console = new Console();
        console.handleArgs(args);

        TuringMachine turingMachine = console.getTuringMachine();

        //Instantiate the states and transitions from a file
        turingMachine.loadFile();

        //Run the turing machine
        turingMachine.simulate();
    }
}
