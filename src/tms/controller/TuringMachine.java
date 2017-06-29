package tms.controller;

import tms.model.Snapshot;
import tms.model.State;
import tms.model.Tape;
import tms.model.Transition;
import tms.thread.RunnableSimulation;
import tms.thread.RunnableSimulationRejectedExecutionHandler;

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
    private ThreadPoolExecutor executorService;
    private ArrayList<RunnableSimulation> runnableSimulations; //runnableSimulations for threads (same number of maxThreads)
    private String file;

    public TuringMachine(String file, String initialTapeWord, int computationLimit , int maxThreads) {
        /*
        Thread instances
         */
        RunnableSimulationRejectedExecutionHandler runnableSimulationRejectedExecutionHandler = new RunnableSimulationRejectedExecutionHandler();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
                /*
        Thread instances
         */
        this.file = file;
        this.executorService = new ThreadPoolExecutor(maxThreads, maxThreads, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(maxThreads), threadFactory, runnableSimulationRejectedExecutionHandler);
        this.states = new HashMap<>();
        this.initialTape = new Tape(initialTapeWord);
        this.initialSymbol = this.initialTape.getHeader();
        this.maxComputation = computationLimit;
        this.maxThreads = maxThreads;

        //Instantiating the runnable threads
        this.runnableSimulations = new ArrayList<>();
        for (int i = 0; i < maxThreads; i++) {
            runnableSimulations.add(i, new RunnableSimulation(i, new Snapshot(states, new Tape(initialTapeWord), initialSymbol), this));
        }
    }

    public void loadFile(){
        FileHandler fileHandler = new FileHandler(this.file);

        try {
            fileHandler.loadMachine(states);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void simulate(){
        //Execute the first thread
        executeThread(new Snapshot(states, initialTape, initialSymbol));


    }

    /**
     *Receive a snapshot and run a new thread for it
     * @param snapshot
     */
    public void executeThread(Snapshot snapshot){
        RunnableSimulation runnableSimulation = getFreeRunnableSimulation();
        if(runnableSimulation == null){
            System.out.println("There is no threads available anymore.");
            return;
        }
        runnableSimulation.setSnapshot(snapshot);
        runnableSimulation.setId(runnableSimulation.getId());
        try{
            executorService.execute(runnableSimulation);
        }
        catch (Error e){
            System.out.println("ExecuteThread Error.");
        }
    }

    /**
     * Terminate the executorService and printout the computations to get there.
     * @param snapshot
     */

    public void accepted(Snapshot snapshot){
        System.out.println(snapshot.getComputations());
        System.out.println("FIM");
        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println("Threads finalized friendly!");
        } catch (InterruptedException e) {
            System.out.println("Forcing shutdown");
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public void notAccepted(Snapshot snapshot){
//        System.out.println(snapshot.getComputations());
        if(executorService.getActiveCount() <= 1){
            try {
                executorService.awaitTermination(10, TimeUnit.MILLISECONDS);
                System.out.println(snapshot.getComputations());
                executorService.shutdown();
                System.out.println("Not Accepted");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Verify if there are free threads and return one. Else, return null.
     * @return RunnableSimulation
     */
    private RunnableSimulation getFreeRunnableSimulation(){
        for (RunnableSimulation runnableSimulation:runnableSimulations) {
            if(!runnableSimulation.isRunning()){
                runnableSimulation = new RunnableSimulation(runnableSimulation.getId(), new Snapshot(states, this.initialTape, initialSymbol), this);
                return runnableSimulation;
            }
        }
        return null;
    }


    /**
     *
     * @param snapshot
     */
    public void handleNonDeterministicTransitions(Snapshot snapshot){
        for (Transition transition : snapshot.getNondeterministicTransitions()) {
            Snapshot simulatedSnapshot =  simulateOneTransition(snapshot, transition);
            if(!executorService.isShutdown()){
                executeThread(simulatedSnapshot);
            }

        }

    }

    private Snapshot simulateOneTransition(Snapshot snapshot, Transition transition){
        Tape newTape = snapshot.getTape().getNewTape();
        Snapshot simulatedSnapshot = new Snapshot(states, snapshot.getInitialState(), newTape, snapshot.getInitialSymbol(), snapshot.getComputations(), null) ;
        simulatedSnapshot.setComputations(simulatedSnapshot.getComputations() + "...n" + simulatedSnapshot.getInitialState() + ":" + simulatedSnapshot.getTape().getTape() + "\n");
        simulatedSnapshot.getTape().write(transition.getWrite());
        simulatedSnapshot.getTape().move(transition.getMovement());
        simulatedSnapshot.setInitialSymbol(simulatedSnapshot.getTape().getHeader());
        simulatedSnapshot.setInitialState(states.get(transition.getNextState()).getId());

        return simulatedSnapshot;
    }

    public void tryToKillExecutor(){
        boolean terminated = true;
        for (RunnableSimulation runnableSimulation : runnableSimulations) {
            if(runnableSimulation.isRunning() == true){
                terminated = false;
                break;
            }
        }
        if(terminated){
            executorService.shutdown();
        }
    }




    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ThreadPoolExecutor executorService) {
        this.executorService = executorService;
    }

    public ArrayList<RunnableSimulation> getRunnableSimulations() {
        return runnableSimulations;
    }

    public void setRunnableSimulations(ArrayList<RunnableSimulation> runnableSimulations) {
        this.runnableSimulations = runnableSimulations;
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
