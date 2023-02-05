package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    Person person1;
    Person person2;

    Chore chore1;
    Chore chore2;

    @BeforeEach
    public void setup() {
        person1 = new Person("Joey");
        person2 = new Person("Chandler");

        chore1 = new Chore("Dishes", "wash dishes", 0.5, Interval.DAILY);
        chore2 = new Chore("Laundry", "wash laundry", 1, Interval.WEEKLY);
    }

    @Test
    public void testConstructor() {
        assertEquals("Joey", person1.getName());
        assertEquals("Chandler", person2.getName());
        assertTrue(person1.getChores().isEmpty());
        assertTrue(person2.getChores().isEmpty());
    }

    @Test
    public void testAssignChoreSingle() {
        person1.assignChore(chore1);
        assertEquals(chore1, person1.getChores().get(0));
        assertEquals(chore1.getTime(), person1.getTime());
        person1.assignChore(chore2);
        assertEquals(chore2, person1.getChores().get(1));
        assertEquals(2, person1.getChores().size());
        assertEquals(chore1.getTime() + chore2.getTime(), person1.getTime());
    }

    @Test
    public void testAssignChoreMulti() {
        person1.assignChore(chore1);
        assertEquals(chore1, person1.getChores().get(0));
        assertFalse(person1.assignChore(chore1));
        assertEquals(chore1, person1.getChores().get(0));
        assertEquals(1, person1.getChores().size());
    }

}
