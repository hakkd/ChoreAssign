package ui;

import model.Chore;
import model.Person;

import java.util.ArrayList;
import java.util.Scanner;

public class ChoreAssign {
    private ArrayList<Person> people;
    private static ArrayList<Chore> chores;
    private Scanner input;

    public ChoreAssign() {
        runChoreAssign();
    }

    private void runChoreAssign() {
        boolean run = true;
        String command = null;

        init();

        while (run) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                run = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes lists
    private void init() {
        people = new ArrayList<>();
        chores = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> create chore");
        System.out.println("\tv -> view chores");
        System.out.println("\te -> edit chore assignments");
        System.out.println("\th -> edit household members");
        System.out.println("\tm -> view household members");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("c")) {
            createChore();
        } else if (command.equals("v")) {
            viewChores();
        } else if (command.equals("e")) {
            editAssignments();
        } else if (command.equals("h")) {
            editMembers();
        } else if (command.equals("m")) {
            viewMembers();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void createChore() {}

    private void viewChores() {}

    private void editAssignments() {}

    private void editMembers() {}

    private void viewMembers() {}
}
