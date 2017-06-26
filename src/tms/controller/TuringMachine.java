package tms.controller;

import tms.model.Snapshot;
import tms.model.State;
import tms.model.Tape;
import tms.model.Transition;
import tms.thread.RunnableSimulation;

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

        Future<Snapshot> future = executorService.submit(new RunnableSimulation(new Snapshot(states, tape, initialSymbol)));

        try {
            Snapshot snapshot = future.get();

            //Verify if there is no transition
            if(snapshot.getNondeterministicTransitions().size() == 0){
                System.out.println("No Transitions");
                executorService.shutdown();
                return;
            }
            //Verify if there are more than one transition, simulate nondeterministic then and send again to the threads
            if(snapshot.getNondeterministicTransitions().size() > 1){
                Tape tape = new Tape(snapshot.getTape().getTape());
                for (Transition transition : snapshot.getNondeterministicTransitions()) {
                    /*
                    TODO: - implement the function below
                     */
                    Snapshot snapshotAux = simulateOneTransition(snapshot);
//                    future = executorService.submit(new RunnableSimulation(new Snapshot(states, snapshot.getInitialState(), tape, transition., snapshot.getComputations(), snapshot.getNondeterministicTransitions())));
                    System.out.println(future.get().getNondeterministicTransitions().toString());
                }

                System.out.println(snapshot.getNondeterministicTransitions().toString());
            }
            else if(snapshot.getNondeterministicTransitions().get(0).isHalt()){//Verify if the Turing Machine was accepted
                System.out.println(snapshot.getComputations().toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

    }

    private Snapshot simulateOneTransition(Snapshot snapshot){



        return null;
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
