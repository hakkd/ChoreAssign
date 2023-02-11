package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChoreTest {

    Chore chore1;
    Chore chore2;
    Chore chore3;

    Person testPerson;

    @BeforeEach
    public void setup() {
        chore1 = new Chore("Dishes", "wash dishes", 0.5, Interval.DAILY);
        chore2 = new Chore("Laundry", "wash laundry", 1, Interval.WEEKLY);
        chore3 = new Chore("Clean Fridge", "wipe down fridge and throw out old food",
                1, Interval.MONTHLY);

        testPerson = new Person("Joey");
    }

    @Test
    public void testConstructor() {
        assertEquals("Dishes", chore1.getName());
        assertEquals("wash dishes", chore1.getDescription());
        assertEquals(0.5, chore1.getTime());
        assertEquals(Interval.DAILY, chore1.getInterval());
        assertEquals(1, chore1.getId());
        assertEquals(2, chore2.getId());
        assertEquals(3, chore3.getId());
    }

    @Test
    public void testAssign() {
        chore1.assign(testPerson);
        assertTrue(chore1.getIsAssigned());
        assertEquals(chore1, testPerson.getChores().get(0));

        chore2.assign(testPerson);
        assertTrue(chore2.getIsAssigned());
        assertEquals(chore2, testPerson.getChores().get(1));
    }

    @Test
    public void testUnassign() {
        chore1.assign(testPerson);
        assertTrue(chore1.getIsAssigned());
        assertEquals(chore1, testPerson.getChores().get(0));

        chore2.assign(testPerson);
        assertTrue(chore2.getIsAssigned());
        assertEquals(chore2, testPerson.getChores().get(1));

        chore1.unassign(testPerson);
        assertFalse(chore1.getIsAssigned());
        assertEquals(chore2, testPerson.getChores().get(0));

        chore2.unassign(testPerson);
        assertFalse(chore2.getIsAssigned());
        assertTrue(testPerson.getChores().isEmpty());
    }

}