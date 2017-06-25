package tms.controller;

import tms.model.Snapshot;
import tms.model.State;
import tms.model.Tape;
import tms.thread.CallableSimulation;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Created by renan on 6/5/17.
 */
public class TuringMachine{

    private HashMap<Integer, State> states;
    private Tape tape;
    private char initialSymbol;

    public TuringMachine(String tape){
        this.states = new HashMap<>();
        this.tape = new Tape(tape);
        initialSymbol = this.tape.getHeader();
    }

    public void loadFile(){
        FileHandler fileHandler = new FileHandler("m1.mt");

        try {
            fileHandler.loadMachine(states);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void simulate(){
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<String> future = executorService.submit(new CallableSimulation(new Snapshot(states, tape, initialSymbol)));

        try {
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

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
}
