package tms.model;

import com.sun.xml.internal.fastinfoset.util.CharArray;

/**
 * Created by renan on 6/22/17.
 */

public class Tape {
    private String tape;
    private String word;
    private int position;


    public Tape(String word) {
        /*Creates a string with the initial word and 20 spaces on both sides*/
        this.tape = "____________________";
        this.tape = this.tape + word;
        this.tape = this.tape + "____________________";
        this.word = word;

        /*Put the position to the first letter*/
        position = 20;
    }

    public String getTape() {
        return tape.substring(0, position) + "<" + tape.substring(position, position+1) + ">" + tape.substring(position+1, tape.length());
    }

    public String getWord(){
        return word;
    }


    public void move(char symbol){
        if(symbol == 'e'){
            moveLeft();
        }
        else if(symbol == 'd'){
            moveRight();
        }
    }


    public void moveLeft(){
        position--;
    }

    public void moveRight(){
        position++;
    }

    public  void write(char symbol){

        /*Verify if the size of the tape is enough*/
        if(tape.length() >= position){
            tape = tape + "  ";
        }
        if(position < 0){
            tape = "  " + tape;
            position = position + 2;
        }

        /*Replace the header char to the new one*/
        char[] tapeAux = tape.toCharArray();
        tapeAux[position] = symbol;
        tape = String.valueOf(tapeAux);
    }

    public char getHeader(){
        return tape.charAt(position);
    }

    public void setTape(String tape) {
        this.tape = tape;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
