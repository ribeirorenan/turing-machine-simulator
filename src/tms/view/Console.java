package tms.view;

import tms.controller.TuringMachine;

import java.util.Scanner;

/**
 * Created by renan on 6/5/17.
 */
public class Console {
    private Scanner keyboard;
    private String file;


    public TuringMachine getTuringMachine(){
        keyboard = new Scanner(System.in);
        int maxThreads;
        int maxComputation;
        String initialWord;
        System.out.println("Oracle Turing Machine Simulator v1.0");
        System.out.println("Implemented for Theory of Computation Course");
        System.out.println("Renan Airton Batista Ribeiro, IFMG Campus Formiga, 2017");
        System.out.println("");
        System.out.println("Type the limit of computations");
        maxComputation = keyboard.nextInt();
        System.out.println("Type the limit of simultaneous Threads");
        maxThreads = keyboard.nextInt();
        System.out.println("Type the Initial word");
        initialWord = keyboard.next();

        TuringMachine turingMachine = new TuringMachine(file, initialWord, maxComputation, maxThreads);

        return turingMachine;
    }

    public void handleArgs(String[] args){

        for (String s : args) {
            if(s.endsWith(".mt")){
                this.file = s;
            }
        }
    }
}
