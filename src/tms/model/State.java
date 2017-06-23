package tms.model;

import java.util.ArrayList;

/**
 * Created by renan on 6/6/17.
 */
public class State {
    int id;
    ArrayList<Transition> transitions;

    public State(int id) {
        this.id = id;
        transitions = new ArrayList<>();
    }

    public int getNumberOfTransitions(char symbol){

        int quantity = 0;

        for (Transition transition:transitions) {
            if(transition.getRead() == symbol){
                quantity++;
            }
        }

        return quantity;
    }


    public Transition getTransition(char symbol){
        for (Transition transition:transitions) {
            if(transition.getRead() == symbol){
                return transition;
            }
        }

        return null;
    }

    public Transition getHaltTransition(){
        for (Transition transition: transitions) {
            if(transition.isHalt()){
                return transition;
            }
        }
        return null;
    }


    public boolean addTransition(Transition transition){
        return transitions.add(transition);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }


    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", Transitions=" + transitions +
                '}';
    }
}
