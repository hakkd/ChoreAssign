package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

import static org.junit.jupiter.api.Assertions.*;

class ChoreAssignTest {

    ChoreAssign choreAssignTest;

    Chore chore1;
    Chore chore2;
    Chore chore3;

    @BeforeEach
    public void setup() {
        choreAssignTest = new ChoreAssign("test");

        chore1 = new Chore("Dishes", "wash dishes", 30, Interval.DAILY);
        chore2 = new Chore("Laundry", "wash laundry", 60, Interval.WEEKLY);
        chore3 = new Chore("Clean Fridge", "wipe down fridge and throw out old food",
                60, Interval.MONTHLY);
    }

    @Test
    public void testConstructorEmpty() {
        assertTrue(choreAssignTest.getChores().isEmpty());
        assertTrue(choreAssignTest.getPeople().isEmpty());
    }

    @Test
    public void testAddOneChore() {
        choreAssignTest.addChore(chore1);
        assertFalse(choreAssignTest.getChores().isEmpty());
    }

    @Test
    public void testAddTwoChores() {
        choreAssignTest.addChore(chore1);
        choreAssignTest.addChore(chore2);
        assertEquals(2, choreAssignTest.getChores().size());
        assertEquals(chore1, choreAssignTest.getChores().get(0));
        assertEquals(chore2, choreAssignTest.getChores().get(1));
    }

    @Test
    public void testAddOnePerson() {
        try {
            choreAssignTest.addPerson("Joey");
        } catch (DuplicatePersonException e) {
            fail("unexpected DuplicatePersonException");
        }
        assertFalse(choreAssignTest.getPeople().isEmpty());
    }

    @Test
    public void testAddTwoPeople() {
        try {
            choreAssignTest.addPerson("Joey");
            choreAssignTest.addPerson("Chandler");
        } catch (DuplicatePersonException e) {
            fail("unexpected DuplicatePersonException");
        }
        assertEquals(2, choreAssignTest.getPeople().size());
        assertEquals("Joey", choreAssignTest.getPeople().get(0).getName());
        assertEquals("Chandler", choreAssignTest.getPeople().get(1).getName());
    }

    @Test
    public void testAddTwoPeopleDuplicate() {
        try {
            choreAssignTest.addPerson("Joey");
            choreAssignTest.addPerson("Joey");
            fail("Did not throw DuplicatePersonException");
        } catch (DuplicatePersonException e) {}
        assertEquals(1, choreAssignTest.getPeople().size());
        assertEquals("Joey", choreAssignTest.getPeople().get(0).getName());
    }

    @Test
    public void testGetChoreEmpty() {
        try {
            choreAssignTest.getChore(1);
            fail("Did not throw IdNotFoundException");
        } catch (IdNotFoundException e) {}
        assertTrue(choreAssignTest.getChores().isEmpty());
    }

    @Test
    public void testGetChoreWrongId() {
        choreAssignTest.addChore(chore1);
        try {
            choreAssignTest.getChore(chore1.getId() - 1);
            fail("Did not throw IdNotFoundException");
        } catch (IdNotFoundException e) {}
        assertEquals(1, choreAssignTest.getChores().size());
    }

    @Test
    public void testGetChoreFirst() {
        choreAssignTest.addChore(chore3);
        choreAssignTest.addChore(chore2);
        choreAssignTest.addChore(chore1);

        try {
            assertEquals(chore1, choreAssignTest.getChore(chore1.getId()));
        } catch (IdNotFoundException e) {
            fail("unexpected IdNotFoundException");
        }
        assertEquals(3, choreAssignTest.getChores().size());
    }

    @Test
    public void testGetChoreSecond() {
        choreAssignTest.addChore(chore3);
        choreAssignTest.addChore(chore2);
        choreAssignTest.addChore(chore1);

        try {
            assertEquals(chore2, choreAssignTest.getChore(chore2.getId()));
        } catch (IdNotFoundException e) {
            fail("unexpected IdNotFoundException");
        }
        assertEquals(3, choreAssignTest.getChores().size());
    }

    @Test
    public void testGetChoreThird() {
        choreAssignTest.addChore(chore3);
        choreAssignTest.addChore(chore2);
        choreAssignTest.addChore(chore1);

        try {
            assertEquals(chore3, choreAssignTest.getChore(chore3.getId()));
        } catch (IdNotFoundException e) {
            fail("unexpected IdNotFoundException");
        }
        assertEquals(3, choreAssignTest.getChores().size());
    }

    @Test
    public void testGetPersonEmpty() {
        try {
            choreAssignTest.getPerson("Joey");
            fail("Did not throw PersonNotFoundException");
        } catch (PersonNotFoundException e) {}
        assertTrue(choreAssignTest.getPeople().isEmpty());
    }

    @Test
    public void testGetPersonWrongName() {
        try {
            choreAssignTest.addPerson("Joey");
        } catch (DuplicatePersonException e){}
        try {
            choreAssignTest.getPerson("Hoey");
            fail("Did not throw PersonNotFoundException");
        } catch (PersonNotFoundException e) {}
        assertEquals(1, choreAssignTest.getPeople().size());
    }

    @Test
    public void testGetPersonFirst() {
        try {
            choreAssignTest.addPerson("Joey");
            choreAssignTest.addPerson("Chandler");
        } catch (DuplicatePersonException e){}
        try {
            assertEquals("Joey", choreAssignTest.getPerson("Joey").getName());
        } catch (PersonNotFoundException e) {
            fail("unexpected PersonNotFoundException");
        }
        assertEquals(2, choreAssignTest.getPeople().size());
    }

    @Test
    public void testGetPersonSecond() {
        try {
            choreAssignTest.addPerson("Joey");
            choreAssignTest.addPerson("Chandler");
        } catch (DuplicatePersonException e) {}
        try {
            assertEquals("Chandler", choreAssignTest.getPerson("Chandler").getName());
        } catch (PersonNotFoundException e) {
            fail("unexpected PersonNotFoundException");
        }
        assertEquals(2, choreAssignTest.getPeople().size());
    }

    //TODO: figure out if can do before each for subset of tests
    @Test
    public void testAssignChore() {
        choreAssignTest.addChore(chore1);
        choreAssignTest.addChore(chore2);
        try {
            choreAssignTest.addPerson("Joey");
            choreAssignTest.addPerson("Chandler");
        } catch (DuplicatePersonException e) {}
        try {
            choreAssignTest.assignChore(chore1.getId(), "Joey");
        } catch (ChoreException e) {
            fail("Unexpected ChoreException");
        } catch (PersonException e) {
            fail("Unexpected PersonException");
        }
        assertFalse(chore2.getIsAssigned());
        assertTrue(chore1.getIsAssigned());
    }

    @Test
    public void testAssignChoreBadPerson() {
        choreAssignTest.addChore(chore1);
        choreAssignTest.addChore(chore2);
        try {
            choreAssignTest.addPerson("Joey");
            choreAssignTest.addPerson("Chandler");
        } catch (DuplicatePersonException e) {}
        try {
            choreAssignTest.assignChore(chore1.getId(), "Hoey");
            fail("Did not throw PersonException");
        } catch (ChoreException e) {
            fail("Unexpected ChoreException");
        } catch (PersonException e) {}
        assertFalse(chore1.getIsAssigned());
        assertFalse(chore2.getIsAssigned());
    }

    @Test
    public void testAssignChoreBadId() {
        choreAssignTest.addChore(chore1);
        choreAssignTest.addChore(chore2);
        try {
            choreAssignTest.addPerson("Joey");
            choreAssignTest.addPerson("Chandler");
            choreAssignTest.assignChore(-1, "Joey");
            fail("Did not throw ChoreException");
        } catch (ChoreException e) {
        } catch (PersonException e) {
            fail("Unexpected PersonException");
        }
        assertFalse(chore1.getIsAssigned());
        assertFalse(chore2.getIsAssigned());
    }

    @Test
    public void testAssignChoreAlreadyAssigned() {
        choreAssignTest.addChore(chore1);
        choreAssignTest.addChore(chore2);
        try {
            choreAssignTest.addPerson("Joey");
            choreAssignTest.addPerson("Chandler");
            choreAssignTest.assignChore(chore1.getId(), "Joey");
            choreAssignTest.assignChore(chore1.getId(), "Chandler");
            fail("Did not throw ChoreAlreadyAssignedException");
        } catch (ChoreException e) {
        } catch (PersonException e) {
            fail("Unexpected PersonException");
        }
        assertTrue(chore1.getIsAssigned());
        assertFalse(chore2.getIsAssigned());
    }

    @Test
    public void testDeleteChore() {
        choreAssignTest.addChore(chore1);
        try {
            choreAssignTest.deleteChore(chore1.getId());
        } catch (IdNotFoundException e) {
            fail("unexpected IdNotFoundException");
        }
        assertTrue(choreAssignTest.getChores().isEmpty());
    }

    @Test
    public void testDeleteChoreBadId() {
        choreAssignTest.addChore(chore1);
        try {
            choreAssignTest.deleteChore(-1);
            fail("Did not throw IdNotFoundException");
        } catch (IdNotFoundException e) {
        }
        assertEquals(1, choreAssignTest.getChores().size());
    }

    @Test
    public void testDeleteChoreRemoveFromPerson() {
        choreAssignTest.addChore(chore1);
        try {
            choreAssignTest.addPerson("Joey");
            choreAssignTest.assignChore(chore1.getId(), "Joey");
            assertEquals(1, choreAssignTest.getPeople().get(0).getChores().size());
            choreAssignTest.deleteChore(chore1.getId());
        } catch (PersonException e) {
        } catch (ChoreException e) {
            fail("unexpected IdNotFoundException");
        }
        assertTrue(choreAssignTest.getChores().isEmpty());
        assertTrue(choreAssignTest.getPeople().get(0).getChores().isEmpty());
    }

    @Test
    public void testDeletePerson() {
        try {
            choreAssignTest.addPerson("Joey");
            assertFalse(choreAssignTest.getPeople().isEmpty());
            choreAssignTest.deletePerson("Joey");
        } catch (DuplicatePersonException e) {
        } catch (PersonNotFoundException e){
            fail("unexpected PersonNotFoundException");
        }
        assertTrue(choreAssignTest.getPeople().isEmpty());
    }

    @Test
    public void testDeletePersonBadPerson() {
        try {
            choreAssignTest.addPerson("Joey");
            assertFalse(choreAssignTest.getPeople().isEmpty());
            choreAssignTest.deletePerson("Hoey");
            fail("Did not throw PersonNotFoundException");
        } catch (DuplicatePersonException e) {
        } catch (PersonNotFoundException e){
        }
        assertFalse(choreAssignTest.getPeople().isEmpty());
    }
}
