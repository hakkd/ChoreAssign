package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Represents the ChoreAssignApp app
//code for persistence implementation based on CPSC210 JsonSerializationDemo
public class ChoreAssignApp {
    private static final String JSON_STORE = "./data/choreassign.json";
    private Scanner input;
    private ChoreAssign choreAssign;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the ChoreAssignApp application
    public ChoreAssignApp() throws FileNotFoundException {
        choreAssign = new ChoreAssign("My Chores");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runChoreAssign();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
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

        System.out.println("\nGet to work!");
    }

    // MODIFIES: this
    // EFFECTS: initializes lists
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> edit chores");
        System.out.println("\tv -> view chores");
        System.out.println("\ta -> assign a chore");
        System.out.println("\te -> edit people");
        System.out.println("\tp -> view people");
        System.out.println("\ts -> save chore assignments to file");
        System.out.println("\tl -> load chore assignments from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("c")) {
            editChores();
        } else if (command.equals("v")) {
            try {
                viewChores(choreAssign.getChores());
            } catch (NoChoresException e) {
                System.out.println("There are no chores");
            }
        } else if (command.equals("a")) {
            assignChore();
        } else if (command.equals("e")) {
            editPeople();
        } else if (command.equals("p")) {
            try {
                viewPeople();
            } catch (PersonException e) {
                System.out.println(e.getMessage());
            }
        } else if (command.equals("s")) {
            saveChoreAssign();
        } else if (command.equals("l")) {
            loadChoreAssign();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: process uses command
    private void editChores() {
        String command = null;
        boolean run = true;
        while (run) {
            displayChoresMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                run = false;
            } else {
                processChoreCommand(command);
            }
        }
    }

    // EFFECTS: display menu of options to user
    private void displayChoresMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> create new chore");
        System.out.println("\td -> delete chore");
        System.out.println("\te -> edit chore");
        System.out.println("\tb -> back");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processChoreCommand(String command) {
        if (command.equals("c")) {
            createChore();
        } else if (command.equals("d")) {
            deleteChore();
        } else if (command.equals("e")) {
            editChore();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to edit fields of chore
    private void editChore() {
        System.out.println("Here are the chores:");
        try {
            viewChores(choreAssign.getChores());
            System.out.println("Enter the ID of a chore to edit");
            int id = input.nextInt();
            Chore chore = choreAssign.getChore(id);
            System.out.println("Which field would you like to edit?");
            System.out.println("\tn -> name");
            System.out.println("\td -> description");
            System.out.println("\ti -> interval");
            System.out.println("\tt -> time");
            String command = input.next();
            processChoreEditCommand(command, chore);
        } catch (ChoreException e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processChoreEditCommand(String command, Chore chore) {
        if (command.equals("n")) {
            editChoreName(chore);
        } else if (command.equals("d")) {
            editChoreDescription(chore);
        } else if (command.equals("t")) {
            editChoreTime(chore);
        } else if (command.equals("i")) {
            editChoreInterval(chore);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // REQUIRES: chore is not null
    // MODIFIES: this
    // EFFECTS: changes chore interval based on user input
    private void editChoreInterval(Chore chore) {
        System.out.println("Enter a new frequency interval");
        Interval oldInterval = chore.getInterval();
        Interval interval = processInterval();
        chore.setInterval(interval);
        System.out.println("Interval was changed from " + oldInterval + " to " + interval);
    }

    // REQUIRES: user input time is not zero
    // MODIFIES: this
    // EFFECTS: changes chore time required to user input value
    private void editChoreTime(Chore chore) {
        System.out.println("Enter a new time");
        double oldTime = chore.getTime();
        int newTime = input.nextInt();
        chore.setTime(newTime);
        System.out.println("Time was changed from " + oldTime + " minutes to " + newTime + " minutes");
    }

    // REQUIRES: chore is not null
    // MODIFIES: this
    // EFFECTS: changes chore description to user input value
    private void editChoreDescription(Chore chore) {
        System.out.println("Enter a new description");
        String newDesc = input.next();
        chore.setDescription(newDesc);
        System.out.println("Description was set to");
        System.out.println("\t" + newDesc);
    }

    // REQUIRES: chore is not null, given name is not empty
    // MODIFIES: this
    // EFFECTS: changes chore name to user input value
    private void editChoreName(Chore chore) {
        System.out.println("Enter a new name (cannot be empty)");
        String oldName = chore.getName();
        String newName = input.next();
        chore.setName(newName);
        System.out.println(oldName + " was renamed to " + newName);
    }

    // MODIFIES: this
    // EFFECTS: removes chore from list of chores
    private void deleteChore() {
        System.out.println("Here are the chores:");
        try {
            viewChores(choreAssign.getChores());
            System.out.println("Enter the ID of the chore you want to delete");
            int id = input.nextInt();
            choreAssign.deleteChore(id);
            System.out.println("Chore with ID " + id + " was deleted.");
        } catch (ChoreException e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new chore using user inputs
    private void createChore() {
        Chore chore;
        System.out.println("Enter chore name (name must not be empty)");
        String name = input.next();
        System.out.println("Enter chore description");
        String desc = input.next();
        System.out.println("Enter chore frequency interval");
        Interval interval = processInterval();
        System.out.println("Enter time required for chore on specified interval (in minutes, must be > 0)");
        int minutes = input.nextInt();

        chore = new Chore(name, desc, minutes, interval);
        choreAssign.addChore(chore);
        System.out.println("Created new chore:");
        System.out.println("ID = " + chore.getId());
        System.out.println("Name = " + chore.getName());
        System.out.println("Description = " + chore.getDescription());
        System.out.println("Interval = " + chore.getInterval());
        System.out.println("Time (minutes) = " + chore.getTime());
    }

    // MODIFIES: this
    // EFFECTS: converts user string input to Interval enum
    private Interval processInterval() {
        String selection = "";  // force entry into loop

        while (!(selection.equals("d") || selection.equals("w") || selection.equals("m"))) {
            System.out.println("\td for daily");
            System.out.println("\tw for weekly");
            System.out.println("\tm for monthly");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("d")) {
            return Interval.DAILY;
        } else if (selection.equals("w")) {
            return Interval.WEEKLY;
        } else {
            return Interval.MONTHLY;
        }
    }

    // EFFECTS: prints chore fields for each chore in user-created chores list
    private void viewChores(ArrayList<Chore> chores) throws NoChoresException {
        if (chores.isEmpty()) {
            throw new NoChoresException("No chores found");
        } else {
            System.out.printf("%-4S %-15S %-25S %-9S %-9S %-10S %n", "id", "name", "description", "time (minutes)",
                    "interval", "assigned?");
            for (Chore c : chores) {
                System.out.printf("%-4d %-15s %-25s %-14d %-9s %-10s %n", c.getId(), c.getName(), c.getDescription(),
                        c.getTime(), c.getInterval(), c.getIsAssigned());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add an unassigned chore to a person's list of chores
    private void assignChore() {
        System.out.println("Here are the available chores:");
        try {
            viewChores(choreAssign.getChores());
            System.out.println("Enter the ID number of the chore you want to assign");
            int id = input.nextInt();
            Chore chore = choreAssign.getChore(id);
            System.out.println("Enter the name of the person to assign the chore to: " + getPeopleNames());
            String name = input.next();
            Person person = choreAssign.getPerson(name);
            choreAssign.assignChore(id, name);
            System.out.println(chore.getName() + " was assigned to " + person.getName());
        } catch (ChoreException e) {
            System.out.println(e.getMessage());
        } catch (PersonException e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void editPeople() {
        String command = null;
        boolean run = true;
        while (run) {
            displayPeopleMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                run = false;
            } else {
                processPeopleCommand(command);
            }
        }
    }

    // EFFECTS: display menu of options to user
    private void displayPeopleMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> create new person");
        System.out.println("\td -> delete person");
        System.out.println("\te -> edit chore assignments");
        System.out.println("\tb -> back");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processPeopleCommand(String command) {
        if (command.equals("c")) {
            createPerson();
        } else if (command.equals("d")) {
            deletePerson();
        } else if (command.equals("e")) {
            editAssignments();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new person according to user input and adds to list of person
    private void createPerson() {
        System.out.println("Enter a name");
        String name = input.next();
        choreAssign.addPerson(name);
        System.out.println("Created new person named " + name);
    }

    // MODIFIES: this
    // EFFECTS: lets user delete person from list of people
    private void deletePerson() {
        System.out.println("Here are the people:");
        try {
            viewPeople();
            System.out.println("Enter the name of the person to delete");
            String name = input.next();
            System.out.println(name + " will be deleted");
            System.out.println("Are you sure (y/n)");
            String response = input.next();
            if (response.equals("y")) {
                choreAssign.deletePerson(name);
                System.out.println(name + " was deleted.");
            }
        } catch (PersonException e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: lets user remove chores for specified person
    private void editAssignments() {
        System.out.println("Here are the people:");
        try {
            viewPeople();
            System.out.println("Enter the name of a person to view chores");
            String name = input.next();
            Person person = choreAssign.getPerson(name);
            viewChores(person.getChores());
            System.out.println("Enter a chore ID to unassign it from " + person.getName());
            int id = input.nextInt();
            Chore chore = choreAssign.getChore(id);
            chore.unassign();
            person.deleteChore(chore);
            System.out.println(chore.getName() + " was unassigned from " + person.getName());
        } catch (PersonException e) {
            System.out.println(e.getMessage());
        } catch (ChoreException e) {
            System.out.println(e.getMessage());
        }
    }

    // EFFECTS: lets user view people or throws exception if list of people is empty
    private void viewPeople() throws NoPeopleException {
        ArrayList<Person> people = choreAssign.getPeople();
        if (people.isEmpty()) {
            throw new NoPeopleException("No people found");
        } else {
            for (Person p: people) {
                System.out.println(p.getName() + " has " + p.getTotalTimeWeekly() + " minutes of chores per week");
                System.out.println("\t" + p.getName() + " is assigned to " + getChoreNames(p.getChores()));
            }
        }
    }

    /**
    // EFFECTS: returns chore with given ID from chores or null if no chores are assigned
    private Chore getChore(int id) {
        Chore chore = null;
        for (Chore c : chores) {
            if (c.getId() == id) {
                chore = c;
            }
        }
        return chore;
    }**/

    // EFFECTS: returns chore with given ID from person's chores or null if no chores are assigned
    private Chore getChorePerson(int id, Person p) {
        Chore chore = null;
        for (Chore c : p.getChores()) {
            if (c.getId() == id) {
                chore = c;
            }
        }
        return chore;
    }

    /**
    // EFFECTS: returns person with given name or null if person doesn't exist
    private Person getPerson(String name) {
        Person person = null;
        for (Person p : people) {
            if (p.getName().equals(name)) {
                person = p;
                break;
            }
            System.out.println("There is no person with that name");
        }
        return person;
    }**/

    // EFFECTS: returns list of names of people
    private ArrayList<String> getPeopleNames() {
        ArrayList<String> names = new ArrayList();
        for (Person p: choreAssign.getPeople()) {
            names.add(p.getName());
        }
        return names;
    }

    // EFFECTS: returns list of names of chores
    private ArrayList<String> getChoreNames(ArrayList<Chore> chores) {
        ArrayList<String> names = new ArrayList();
        for (Chore c : chores) {
            names.add(c.getName());
        }
        return names;
    }

    private void saveChoreAssign() {
        try {
            jsonWriter.open();
            jsonWriter.write(choreAssign);
            jsonWriter.close();
            System.out.println("Saved " + choreAssign.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadChoreAssign() {
        try {
            jsonWriter.open();
            jsonWriter.write(choreAssign);
            jsonWriter.close();
            System.out.println("Saved " + choreAssign.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
