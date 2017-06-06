package tms.controller;

import java.io.*;

/**
 * Created by renan on 6/5/17.
 */
public class FileHandler {

    private String path;
    private BufferedReader fileReader;

    public FileHandler(String path) {

        this.path = path;
    }




    public void printFile()throws IOException {
        fileReader = new BufferedReader(new FileReader(path));

        String line = "";

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
