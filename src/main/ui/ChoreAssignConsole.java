package ui;

import java.io.FileNotFoundException;

public class ChoreAssignConsole {

    public static void main(String[] args) {
        try {
            new ChoreAssignApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}