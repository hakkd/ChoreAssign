package model;

import java.util.ArrayList;
import model.Chore;
import model.Person;
import model.Interval;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

//represents ChoreAssignApp with list of chores and list of people
public class ChoreAssign implements Writable {

    private String name;
    private ArrayList<Person> people;
    private static ArrayList<Chore> chores;

    public ChoreAssign(String name) {
        this.name = name;
        people = new ArrayList<>();
        chores = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Chore> getChores() {
        return chores;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    //MODIFIES: this
    //EFFECTS: adds given chore to list of chores
    public void addChore(Chore chore) {
        chores.add(chore);
    }

    // EFFECTS: returns chore with given ID from chores or null if no chores are assigned
    public Chore getChore(int id) throws IdNotFoundException {
        Chore chore = null;
        for (Chore c : chores) {
            if (c.getId() == id) {
                chore = c;
            }
        }
        if (chore == null) {
            throw new IdNotFoundException();
        } else {
            return chore;
        }
    }

    //MODIFIES: this
    //EFFECTS: unassigns chore from people and deletes it from list of chores
    public void deleteChore(int id) throws IdNotFoundException {
        Chore chore = getChore(id);
        for (Person p: people) {
            p.deleteChore(chore);
        }
        chores.remove(chore);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("people", peopleToJson());
        json.put("chores", choresToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray peopleToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Person p : people) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    private JSONArray choresToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Chore c: chores) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
