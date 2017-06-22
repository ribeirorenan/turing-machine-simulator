import tms.model.TuringMachine;

/**
 * Created by renan on 6/5/17.
 */
public class Main {
    public static void main(String[] args) {

        TuringMachine turingMachine = new TuringMachine("aabb");

        //Instantiate the states and transitions from a file
        turingMachine.loadFile();

        //Run the turing machine

        System.out.println(turingMachine.getTape().getHeader());
        turingMachine.getTape().write('c');
        System.out.println(turingMachine.getTape().getHeader());
        turingMachine.getTape().moveRight();
        System.out.println(turingMachine.getTape().getHeader());
    }
}
