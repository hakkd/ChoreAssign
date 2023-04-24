package persistence;

import model.*;
import model.exceptions.ChoreException;
import model.exceptions.PersonException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads ChoreAssignApp from JSON data stored in file
public class JsonReader {

    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads application state from JSON file and returns it
    public ChoreAssign read() throws IOException, PersonException, ChoreException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseChoreAssign(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: parses ChoreAssign data from JSON and returns it
    private ChoreAssign parseChoreAssign(JSONObject jsonObject) throws PersonException,
            ChoreException {
        String name = jsonObject.getString("name");
        ChoreAssign ca = new ChoreAssign(name);
        addChores(ca, jsonObject);
        addPeople(ca, jsonObject);
        return ca;
    }

    // EFFECTS: parses chores from JSON object and adds them to ChoreAssign object
    private void addChores(ChoreAssign ca, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("chores");
        for (Object json : jsonArray) {
            JSONObject nextChore = (JSONObject) json;
            addChore(ca, nextChore);
        }
    }

    // MODIFIES: ca
    // EFFECTS: parses chore from JSON object and adds it to ChoreAssign object. Only adds unassigned chores.
    private void addChore(ChoreAssign ca, JSONObject jsonObject) {
        int id = jsonObject.getInt(("id"));
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        int time = jsonObject.getInt("time");
        Interval interval = Interval.valueOf(jsonObject.getString("interval"));
        boolean isAssigned = jsonObject.getBoolean("isAssigned");
        Chore chore = new Chore(name, description, time, interval);
        chore.setId(id);
        if (!isAssigned) {
            ca.addChore(chore);
        }
    }

    // EFFECTS: parses people from JSON object and adds them to ChoreAssign object
    private void addPeople(ChoreAssign ca, JSONObject jsonObject) throws PersonException,
            ChoreException {
        JSONArray jsonArray = jsonObject.getJSONArray("people");
        for (Object json : jsonArray) {
            JSONObject nextPerson = (JSONObject) json;
            addPerson(ca, nextPerson);
        }
    }

    // MODIFIES: ca
    // EFFECTS: parses person from JSON object and adds it to ChoreAssign object. Adds assigned chores to ChoreAssign.
    private void addPerson(ChoreAssign ca, JSONObject jsonObject) throws PersonException,
            ChoreException {
        String name = jsonObject.getString("name");
        Person person = new Person(name);
        ca.addPerson(name);

        JSONArray jsonChores = jsonObject.getJSONArray("chores");
        for (Object json : jsonChores) {
            JSONObject nextChore = (JSONObject) json;
            assignAddChore(ca, nextChore, person);
        }
    }

    //MODIFIES person, chore
    //EFFECTS: adds chore to person's list of chores
    public void assignAddChore(ChoreAssign ca, JSONObject jsonObject, Person person) throws PersonException,
            ChoreException {
        int id = jsonObject.getInt(("id"));
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        int time = jsonObject.getInt("time");
        Interval interval = Interval.valueOf(jsonObject.getString("interval"));
        boolean isAssigned = jsonObject.getBoolean("isAssigned");
        if (!isAssigned) {
            throw new ChoreException("Incorrect assigned status");
        }
        Chore chore = new Chore(name, description, time, interval);
        chore.setId(id);
        chore.unassign(); //set as unassigned in order to assign to person
        ca.addChore(chore);
        ca.assignChore(chore.getId(), person.getName());
    }
}
