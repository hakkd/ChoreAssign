package model;

/** The Chore class represents a household chore. A chore has a name, description, time required to complete
 *  and an interval. **/

public class Chore {
    // fields
    private String name; // name of chore
    private String description; // description of chore
    private double time; // time taken to complete in hours
    private Interval interval;
    private boolean isAssigned; // True if has been assigned, else false

    // EFFECTS: constructs a chore
    public Chore(String name, String description, double time,  Interval interval) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.interval = interval;
        this.isAssigned = false;
    }

    // MODIFIES: this
    // EFFECTS: changes assignment status of chore to true (assigned)
    public void assign(Person person) {
        if (person.assignChore(this) != false) {
            this.isAssigned = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: changes assignment status of chore to false (unassigned)
    public void unassign(Person person) {
        this.isAssigned = false;
        person.deleteChore(this);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getTime() {
        return time;
    }

    public Interval getInterval() {
        return this.interval;
    }

    public Boolean getIsAssigned() {
        return this.isAssigned;
    }
}
