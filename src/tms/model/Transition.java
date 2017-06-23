package tms.model;

/**
 * Created by renan on 6/21/17.
 */
public interface Transition {
    public boolean isHalt();
    public char getRead();
    public char getWrite();
    public char getMovement();
    public int getNextState();
    public String getMessage();
}
