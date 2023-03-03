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

    public ArrayList<Chore> getChores() {
        return chores;
    }

    public ArrayList<Person> getPeople() {
        return people;
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
