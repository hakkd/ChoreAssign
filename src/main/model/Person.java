package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/** The Person class represents a person in a household **/

public class Person implements Writable {
    // fields
    private String name; // name
    private ArrayList<Chore> chores; // list of assigned chores
    private double time; // time taken by assigned chores

    // REQUIRES: name length is > 0
    // EFFECTS: constructs a new person
    public Person(String name) {
        this.name = name;
        this.chores = new ArrayList();
        this.time = 0.0;
    }

    // REQUIRES: chore has not already been assigned to this person
    // MODIFIES: this
    // EFFECTS: add chore to person if not already assigned. Add chore time to total assigned time.
    public boolean assignChore(Chore chore) {
        boolean result = true;
        if (!this.getChores().contains(chore)) {
            this.chores.add(chore);
            this.time += chore.getTime();
        } else {
            result = false;
        }
        return result;
    }

    // REQUIRES: chores contains specified chore
    // MODIFIES: this
    // EFFECTS: remove chore from list of chores at specified index
    public void deleteChore(Chore chore) {
        double t = 0;
        for (Chore c: this.chores) {
            if (c == chore) {
                t = c.getTime();
            }
        }
        this.time -= t;
        this.chores.remove(chore);
    }

    // MODIFIES: this
    // EFFECTS: removes all chores from person
    public void deleteAllChores() {
        ArrayList<Chore> chores = this.getChores();
        for (Chore c: chores) {
            c.unassign();
        }
        this.chores.removeAll(chores);
    }

    // EFFECTS: returns sum of time required for all chores assigned to person for a week.
    // Assume 4 weeks in a month.
    public int getTotalTimeWeekly() {
        ArrayList<Chore> chores = this.getChores();

        int total = 0;
        for (Chore c: chores) {
            int t = c.getTime();
            if (c.getInterval() == Interval.DAILY) {
                total += 7 * t;
            } else if (c.getInterval() == Interval.WEEKLY) {
                total += t;
            } else {
                total += t / 4;
            }
        }
        return total;
    }

    // EFFECTS: returns sum of time required for all chores assigned to person for a week.
    // Assume 4 weeks in a month.
    public int getTotalTimeMonthly() {
        return this.getTotalTimeWeekly() * 4;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Chore> getChores() {
        return chores;
    }

    public double getTime() {
        return time;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("chores", chores);
        return json;
    }
}