package tms.model;

/**
 * Created by renan on 6/6/17.
 */
public class CommonTransition implements Transition {
    private char read;
    private char write;
    private char movement;
    private int nextState;

    public CommonTransition(char read, char write, char movement, int nextState) {
        this.read = read;
        this.write = write;
        this.movement = movement;
        this.nextState = nextState;
    }

    @Override
    public String toString() {
        return "CommonTransition{" +
                "read=" + read +
                ", write=" + write +
                ", movement=" + movement +
                ", nextState=" + nextState +
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

    public int getNextState() {
        return nextState;
    }

    public void setNextState(int nextState) {
        this.nextState = nextState;
    }

    @Override
    public boolean isHalt() {
        return false;
    }
}
