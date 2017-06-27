package tms.controller;

import tms.model.Snapshot;
import tms.model.State;
import tms.model.Tape;
import tms.model.Transition;
import tms.thread.RunnableSimulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Created by renan on 6/5/17.
 */
public class TuringMachine{

    private HashMap<Integer, State> states; //States of the machine
    private Tape initialTape; //Initial tape (each thread are going to have his own tape)
    private char initialSymbol;
    private int maxComputation; //Max number of computations, delimited byr the user
    private int maxThreads; //Max number of threads, delimited byr the user
    private ExecutorService executorService;
    private ArrayList<RunnableSimulation> runnableSimulations; //runnableSimulations for threads (same number of maxThreads)


    public TuringMachine(String initialTapeWord, int computationLimit , int maxThreads) {
        this.states = new HashMap<>();
        this.initialTape = new Tape(initialTapeWord);
        this.initialSymbol = this.initialTape.getHeader();
        this.maxComputation = computationLimit;
        this.maxThreads = maxThreads;
        this.executorService = Executors.newFixedThreadPool(this.maxThreads);
        //Instantiating the runnable threads
        this.runnableSimulations = new ArrayList<>();
        for (int i = 0; i < maxThreads; i++) {
            runnableSimulations.add(i, new RunnableSimulation(new Snapshot(states, new Tape(initialTapeWord), initialSymbol), this));
        }
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
        //Execute the first thread
        executeThread(new Snapshot(states, initialTape, initialSymbol));


        if(executorService.isTerminated()){
            executorService.shutdown();
            System.out.println("Terminated By The Main");
        }


    }

    /**
     *Receive a snapshot and run a new thread for it
     * @param snapshot
     */
    public void executeThread(Snapshot snapshot){
        RunnableSimulation runnableSimulation = getFreeRunnableSimulation();
        runnableSimulation.setSnapshot(snapshot);
        try{
            executorService.execute(runnableSimulation);

        }
        catch (Error e){
            System.out.println("AI AI AI AI AI MEU PIRU");
        }
    }

    /**
     * Terminate the executorService and printout the computations to get there.
     * @param snapshot
     */

    public void accepted(Snapshot snapshot){
        System.out.println(snapshot.getComputations());
        if(!executorService.isShutdown()){
            executorService.shutdown();
        }
    }


    /**
     * Verify if there are free threads and return one. Else, return null.
     * @return RunnableSimulation
     */
    private RunnableSimulation getFreeRunnableSimulation(){
        int i = 0;
        for (RunnableSimulation runnableSimulation:runnableSimulations) {
            if(!runnableSimulation.isRunning()){
                runnableSimulation.setId(i);
                runnableSimulation = new RunnableSimulation(new Snapshot(states, this.initialTape, initialSymbol), this);
                return runnableSimulation;
            }
            i++;
        }
        return null;
    }


    /**
     *
     * @param snapshot
     */
    public void handleNonDeterministicTransitions(Snapshot snapshot){
        System.out.println(snapshot.getNondeterministicTransitions().toString());

        for (Transition transition : snapshot.getNondeterministicTransitions()) {
            Snapshot simulatedSnapshot =  simulateOneTransition(snapshot, transition);
            System.out.println(simulatedSnapshot.getComputations());
            System.out.println("penis");
            executeThread(simulatedSnapshot);
        }

        accepted(snapshot);
    }



    private Snapshot simulateOneTransition(Snapshot snapshot, Transition transition){
        Snapshot simulatedSnapshot = new Snapshot(states, snapshot.getInitialState(), new Tape(snapshot.getTape().getWord()), snapshot.getInitialSymbol(), snapshot.getComputations(), null) ;

        simulatedSnapshot.setComputations(simulatedSnapshot.getComputations() + "...n" + simulatedSnapshot.getInitialState() + ":" + snapshot.getTape().getTape() + "\n");
        simulatedSnapshot.getTape().write(transition.getWrite());
        simulatedSnapshot.getTape().move(transition.getMovement());
        simulatedSnapshot.setInitialSymbol(simulatedSnapshot.getTape().getHeader());
        simulatedSnapshot.setInitialState(states.get(transition.getNextState()).getId());

        return simulatedSnapshot;
    }


    public HashMap<Integer, State> getStates() {
        return states;
    }

    public void setStates(HashMap<Integer, State> states) {
        this.states = states;
    }

    public Tape getInitialTape() {
        return initialTape;
    }

    public void setInitialTape(Tape initialTape) {
        this.initialTape = initialTape;
    }

    public char getInitialSymbol() {
        return initialSymbol;
    }

    public void setInitialSymbol(char initialSymbol) {
        this.initialSymbol = initialSymbol;
    }

    public int getMaxComputation() {
        return maxComputation;
    }

    public void setMaxComputation(int maxComputation) {
        this.maxComputation = maxComputation;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }
}
