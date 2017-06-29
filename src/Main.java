import tms.controller.TuringMachine;

/**
 * Created by renan on 6/5/17.
 */
public class Main {
    public static void main(String[] args) {

        TuringMachine turingMachine = new TuringMachine("<a>", 100, 20);

        //Instantiate the states and transitions from a file
        turingMachine.loadFile();

        //Run the turing machine
        turingMachine.simulate();
    }
}
