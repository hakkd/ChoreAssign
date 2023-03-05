package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testJsonReaderFileNotFound() {
        JsonReader reader = new JsonReader("./data/none.json");
        try {
            ChoreAssign ca = reader.read();
            fail("Did not throw IOException");
        } catch (IOException e) {}
    }

    @Test
    public void testJsonReaderEmptyChoreAssign() {
        JsonReader reader = new JsonReader("./data/testJsonWriterEmptyChoreAssign.json");
        try {
            ChoreAssign ca = reader.read();
            assertEquals("My chores", ca.getName());
            assertEquals(0, ca.getChores().size());
            assertEquals(0, ca.getPeople().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testJsonReaderGeneralChoreAssign() {
        JsonReader reader = new JsonReader("./data/testJsonWriterGeneralChoreAssign.json");
        try {
            ChoreAssign ca = reader.read();
            Chore chore1 = ca.getChores().get(0);
            ArrayList<Chore> chores1 = new ArrayList<>();
            chores1.add(chore1);
            Chore chore2 = ca.getChores().get(1);
            ArrayList<Chore> chores2 = new ArrayList<>();
            chores2.add(chore2);
            assertEquals("My chores", ca.getName());
            List<Chore> chores = ca.getChores();
            List<Person> people = ca.getPeople();
            assertEquals(2, chores.size());
            checkChore("dishes", "wash dishes", Interval.DAILY, 15, chores.get(0));
            checkChore("laundry", "wash laundry", Interval.WEEKLY, 60, chores.get(1));
            checkPerson("Joey", chores1, people.get(0));
            checkPerson("Chandler", chores2, people.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
