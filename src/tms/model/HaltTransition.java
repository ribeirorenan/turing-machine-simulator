package tms.model;

/**
 * Created by renan on 6/21/17.
 */
public class HaltTransition implements Transition {
    private String haltMessage;

    public HaltTransition(String haltMessage) {
        this.haltMessage = haltMessage;
    }

    public String getHaltMessage() {
        return haltMessage;
    }

    public void setHaltMessage(String haltMessage) {
        this.haltMessage = haltMessage;
    }

    @Override
    public String toString() {
        return "HaltTransition{" +
                "haltMessage='" + haltMessage + '\'' +
                '}';
    }

    @Override
    public boolean isHalt() {
        return true;
    }
}
