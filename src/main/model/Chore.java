package model;

/** The Chore class represents a household chore. A chore has a name, description, time required to complete
 *  and an interval. **/

public class Chore {
    // fields
    private static int nextId = 1;
    private int id;
    private String name; // name of chore
    private String description; // description of chore
    private double time; // time taken to complete in hours
    private Interval interval;
    private boolean isAssigned; // True if has been assigned, else false

    // REQUIRES: name has length > 0, time is > 0
    // EFFECTS: constructs a chore
    public Chore(String name, String description, double time,  Interval interval) {
        this.id = nextId++;
        this.name = name;
        this.description = description;
        this.time = time;
        this.interval = interval;
        this.isAssigned = false;
    }

    // MODIFIES: this
    // EFFECTS: changes assignment status of chore to true (assigned)
    public void assign(Person person) {
        if (person.assignChore(this)) {
            this.isAssigned = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: changes assignment status of chore to false (unassigned)
    public void unassign(Person person) {
        this.isAssigned = false;
        person.deleteChore(this);
    }

    public int getId() {
        return id;
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
