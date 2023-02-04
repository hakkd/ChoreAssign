package model;

import java.util.ArrayList;

public class ListOfChore {
    private ArrayList<Chore> chores;

    public void listOfChore() {
        this.chores = new ArrayList<Chore>();
    }

    // MODIFIES: this
    // EFFECTS: add chore to list
    public void addChore(Chore chore) {
        this.chores.add(chore);
    }

    public ArrayList<Chore> getChores() {
        return chores;
    }
}
