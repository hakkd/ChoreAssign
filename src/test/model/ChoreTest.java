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
        assertTrue(chore1.getId() > 0);
        assertTrue(chore2.getId() > chore1.getId());
    }

    @Test
    public void testAssign() {
        chore1.assign(testPerson);
        assertTrue(chore1.getIsAssigned());
        assertEquals(chore1, testPerson.getChores().get(0));
        // try to assign chore 1 again
        chore1.assign(testPerson);
        // check that person still has only one chore assigned
        assertEquals(1, testPerson.getChores().size());

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

        chore1.unassign();
        assertFalse(chore1.getIsAssigned());

        chore2.unassign();
        assertFalse(chore2.getIsAssigned());
    }

    @Test
    public void testSetName() {
        chore1.setName("laundry");
        assertEquals("laundry", chore1.getName());
    }

    @Test
    public void testSetDescription() {
        chore1.setDescription("wash laundry");
        assertEquals("wash laundry", chore1.getDescription());

        chore1.setDescription("");
        assertEquals("", chore1.getDescription());
    }

    @Test
    public void testSetTime() {
        chore1.setTime(12.1);
        assertEquals(12.1, chore1.getTime());

        chore1.setTime(3);
        assertEquals(3, chore1.getTime());
    }

    @Test
    public void testSetInterval () {
        chore1.setInterval(Interval.MONTHLY);
        assertEquals(Interval.MONTHLY, chore1.getInterval());
    }
}