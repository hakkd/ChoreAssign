package persistence;

import model.Chore;
import model.Interval;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    public void checkChore(String name, String description, Interval interval, int time, Chore chore) {
        assertEquals(name, chore.getName());
        assertEquals(description, chore.getDescription());
        assertEquals(time, chore.getTime());
        assertEquals(interval, chore.getInterval());
    }

    public void checkPerson(String name, ArrayList<Chore> chores, Person person) {
        assertEquals(name, person.getName());
        assertEquals(chores, person.getChores());
    }
}
