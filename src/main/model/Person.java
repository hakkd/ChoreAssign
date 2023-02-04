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

    // REQUIRES: Interval is Interval.MONTHLY or Interval.WEEKLY
    // EFFECTS: returns sum of time required for all chores assigned to person for a week or month.
    // Assume 4 weeks in a month.
    // !!! make two methods for weekly and monthly
    public Double getTotalTime(Interval interval) {
        ArrayList<Chore> chores = getChores();

        Double total = 0.0;
        for (Chore c: chores) {
            Double t = c.getTime();
            if (c.getInterval() == Interval.DAILY) {
                if (interval == Interval.WEEKLY) {
                    total += 7 * t;
                } else {
                    total += 7 * 4 * t;
                }
            } else if (c.getInterval() == Interval.WEEKLY) {
                if (interval == Interval.WEEKLY) {
                    total += t;
                } else {
                    total += 4 * t;
                }
            } else if (c.getInterval() == Interval.MONTHLY) {
                if (interval == Interval.WEEKLY) {
                    total += t / 4;
                } else {
                    total += t;
                }
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