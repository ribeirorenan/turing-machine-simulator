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
        transitions = new ArrayList<Transition>();
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
                ", transitions=" + transitions +
                '}';
    }
}
