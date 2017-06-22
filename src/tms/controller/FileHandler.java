package tms.controller;

import tms.model.CommonTransition;
import tms.model.HaltTransition;
import tms.model.State;
import tms.model.Transition;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by renan on 6/5/17.
 */
public class FileHandler {

    private String path;
    private BufferedReader fileReader;

    public FileHandler(String path) {

        this.path = path;
    }


    public void loadMachine(HashMap<Integer, State> states)throws IOException {
        fileReader = new BufferedReader(new FileReader(path));


        String line;

        while (true){

            line = fileReader.readLine();

            if(line == null) {
                break;
            }
            /*
             * Handling the string line
             */
            if(!line.startsWith(";")){ //ignoring comments
                createState(line, states);
            }
        }

        fileReader.close();
    }

    private Transition createState(String line, HashMap<Integer, State> states){
        StringTokenizer tokenizer = new StringTokenizer(line);
        int actualState;
        char read;
        char write;
        char movement;
        int nextState;

        if(!tokenizer.hasMoreElements()){
            return null;
        }

        if(tokenizer.countTokens() == 6){
            /* Get the tokens and generate the values for a new transition
             */
            actualState = Integer.parseInt(tokenizer.nextToken());
            read = tokenizer.nextToken().charAt(0);
            tokenizer.nextToken();
            write = tokenizer.nextToken().charAt(0);
            movement = tokenizer.nextToken().charAt(0);
            nextState = Integer.parseInt(tokenizer.nextToken());

            /*Add transition (and create new states)*/
            Transition transition = new CommonTransition(read, write, movement, nextState);

            if(!states.containsKey(actualState)){
                states.put(actualState, new State(actualState));
            }

            states.get(actualState).addTransition(transition);

        }else if (tokenizer.countTokens() == 2){
            actualState = Integer.parseInt(tokenizer.nextToken());
            Transition transition = new HaltTransition(tokenizer.nextToken());

            if(!states.containsKey(actualState)){
                states.put(actualState, new State(actualState));
            }

            states.get(actualState).addTransition(transition);
        }


        return null;
    }



    public void printFile()throws IOException {
        fileReader = new BufferedReader(new FileReader(path));

        String line;

        while (true){

            line = fileReader.readLine();

            if(line == null) {
                break;
            }
            System.out.println(line);
        }

        fileReader.close();
    }

}
