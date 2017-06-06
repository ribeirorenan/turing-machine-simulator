package tms.model;

/**
 * Created by renan on 6/6/17.
 */
public class Transition {
    private char read;
    private char write;
    private char movement;
    private int state;

    public Transition(char read, char write, char movement, int nextState) {
        this.read = read;
        this.write = write;
        this.movement = movement;
        this.state = nextState;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "read=" + read +
                ", write=" + write +
                ", movement=" + movement +
                ", state=" + state +
                '}';
    }

    public char getRead() {
        return read;
    }

    public void setRead(char read) {
        this.read = read;
    }

    public char getWrite() {
        return write;
    }

    public void setWrite(char write) {
        this.write = write;
    }

    public char getMovement() {
        return movement;
    }

    public void setMovement(char movement) {
        this.movement = movement;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
