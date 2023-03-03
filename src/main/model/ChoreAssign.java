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
}
