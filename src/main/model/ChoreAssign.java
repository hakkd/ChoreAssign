package model;

import java.util.ArrayList;

import model.Chore;
import model.Person;
import model.Interval;
import org.json.JSONArray;
import org.json.JSONObject;
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

    //REQUIRES: person with given name doesn't already exist
    //MODIFIES: this
    //EFFECTS: adds person with given name to list of people
    public void addPerson(String name) throws DuplicatePersonException {
        for (Person p: people) {
            if (p.getName().equals(name)) {
                throw new DuplicatePersonException("A person already exists with that name");
            }
        }
        Person person = new Person(name);
        people.add(person);
    }

    // EFFECTS: returns chore with given ID from chores or throws exception if not found
    public Chore getChore(int id) throws IdNotFoundException {
        Chore chore = null;
        for (Chore c : chores) {
            if (c.getId() == id) {
                chore = c;
            }
        }
        if (chore == null) {
            throw new IdNotFoundException("Chore not found");
        } else {
            return chore;
        }
    }

    //EFFECTS: returns person with given name or throws exception if not found
    public Person getPerson(String name) throws PersonNotFoundException {
        Person person = null;
        for (Person p : people) {
            if (p.getName().equals(name)) {
                person = p;
            }
        }
        if (person == null) {
            throw new PersonNotFoundException("Person not found");
        } else {
            return person;
        }
    }

    //REQUIRES: chore is not already assigned, person with name exists
    //MODIFIES: this, Chore
    //EFFECTS: assigns given chore to person with given name. Throws exception if chore is already assigned
    public void assignChore(int id, String name) throws ChoreAlreadyAssignedException, PersonNotFoundException,
            IdNotFoundException {
        Chore chore = null;
        for (Chore c : chores) {
            if (c.getId() == id) {
                if (c.getIsAssigned()) {
                    throw new ChoreAlreadyAssignedException("Chore is already assigned");
                } else {
                    chore = c;
                }
            }
        }
        if (chore == null) {
            throw new IdNotFoundException("Chore not found");
        }
        Person person = getPerson(name);
        chore.assign(person);
    }

    //REQUIRES: chore with given ID exists
    //MODIFIES: this
    //EFFECTS: unassigns chore from people and deletes it from list of chores
    public void deleteChore(int id) throws IdNotFoundException {
        Chore chore = getChore(id);
        for (Person p: people) {
            p.deleteChore(chore);
        }
        chores.remove(chore);
    }

    //MODIFIES: this
    //EFFECTS: removes person from list of people and unassigns their chores
    public void deletePerson(String name) throws PersonNotFoundException {
        Person person = getPerson(name);
        person.deleteAllChores();
        people.remove(person);
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
