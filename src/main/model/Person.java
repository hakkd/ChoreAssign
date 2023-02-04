package model;

import java.util.ArrayList;

/** The Person class represents a person in a household **/

public class Person {
    // fields
    private String name; // name
    private ArrayList<Chore> chores; // list of assigned chores
    private Double time; // time taken by assigned chores

    // EFFECTS: constructs a new person
    public void person(String name) {
        this.name = name;
        this.chores = new ArrayList();
        this.time = 0.0;
    }

    // MODIFIES: this
    // EFFECTS: add chore to person
    public void assignChore(Chore chore) {
        this.chores.add(chore);
    }

    // EFFECTS: returns sum of time required for all chores assigned to person for a week.
    // Assume 4 weeks in a month.
    public Double getTotalTimeWeekly() {
        ArrayList<Chore> chores = this.getChores();

        Double total = 0.0;
        for (Chore c: chores) {
            Double t = c.getTime();
            if (c.getInterval() == Interval.DAILY) {
                total += 7 * t;
            } else if (c.getInterval() == Interval.WEEKLY) {
                total += t;
            } else if (c.getInterval() == Interval.MONTHLY) {
                total += t / 4;
            }
        }
        return total;
    }

    // EFFECTS: returns sum of time required for all chores assigned to person for a week.
    // Assume 4 weeks in a month.
    public Double getTotalTimeMonthly() {
        ArrayList<Chore> chores = this.getChores();

        Double total = 0.0;
        for (Chore c: chores) {
            Double t = c.getTime();
            if (c.getInterval() == Interval.DAILY) {
                total += 7 * 4 * t;
            } else if (c.getInterval() == Interval.WEEKLY) {
                total += 4 * t;
            } else if (c.getInterval() == Interval.MONTHLY) {
                total += t;
            }
        }
        return total;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Chore> getChores() {
        return chores;
    }
}