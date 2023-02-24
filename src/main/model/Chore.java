package model;

import org.json.JSONObject;
import persistence.Writable;

/** The Chore class represents a household chore. A chore has a name, description, time required to complete
 *  and an interval. **/

public class Chore implements Writable {
    // fields
    private static int nextId = 1;
    private int id;
    private String name; // name of chore
    private String description; // description of chore
    private int time; // time taken to complete in minutes
    private Interval interval;
    private boolean isAssigned; // True if has been assigned, else false

    // REQUIRES: name has length > 0, time is > 0
    // EFFECTS: constructs a chore
    public Chore(String name, String description, int time, Interval interval) {
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
    public void unassign() {
        this.isAssigned = false;
    }

    // REQUIRES: given name is not empty
    // MODIFIES: this
    // EFFECTS: changes the chore name field
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: changes the chore description field
    public void setDescription(String description) {
        this.description = description;
    }

    // REQUIRES: given time is not zero
    // MODIFIES: this
    // EFFECTS: changes the chore time field
    public void setTime(int time) {
        this.time = time;
    }

    // MODIFIES: this
    // EFFECTS: changes the chore interval field
    public void setInterval(Interval interval) {
        this.interval = interval;
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

    public int getTime() {
        return time;
    }

    public Interval getInterval() {
        return this.interval;
    }

    public Boolean getIsAssigned() {
        return this.isAssigned;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("description", description);
        json.put("time", time);
        json.put("interval", interval);
        json.put("isAssigned", isAssigned);
        return json;
    }
}
