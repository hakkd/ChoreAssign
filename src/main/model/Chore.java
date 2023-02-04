package model;

/** The Chore class represents a household chore. A chore has a name, description, time required to complete
 *  and an interval. **/

public class Chore {
    // fields
    private String name; // name of chore
    private String description; // description of chore
    private Double time; // time taken to complete in hours
    private Interval interval;
    private boolean isAssigned; // True if has been assigned, else false

    // EFFECTS: constructs a chore
    public void chore(String name, String description, Double time,  Interval interval) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.interval = interval;
        this.isAssigned = false;
    }

    // MODIFIES: this
    // EFFECTS: changes name of chore
    public void editChoreName(Chore chore, String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: changes description of chore
    public void editChoreDesc(Chore chore, String desc) {
        this.description = desc;
    }

    // MODIFIES: this
    // EFFECTS: changes time of chore
    public void editChoreTime(Chore chore, Double time) {
        this.time = time;
    }

    // MODIFIES: this
    // EFFECTS: changes interval of chore
    public void editChoreInterval(Chore chore, Interval interval) {
        this.interval = interval;
    }

    // MODIFIES: this
    // EFFECTS: changes assignment status of chore to true (assigned)
    public void assign() {
        this.isAssigned = true;
    }

    // MODIFIES: this
    // EFFECTS: changes assignment status of chore to false (unassigned)
    public void unAssign() {
        this.isAssigned = false;
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
