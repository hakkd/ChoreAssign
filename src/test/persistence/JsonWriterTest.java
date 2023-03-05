package persistence;

import model.*;
import model.ChoreAssign;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ChoreAssign ca = new ChoreAssign("My chores");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {}
    }

    @Test
    void testJsonWriterEmptyChoreAssign() {
        try {
            ChoreAssign ca = new ChoreAssign("My chores");
            JsonWriter writer = new JsonWriter("./data/testJsonWriterEmptyChoreAssign.json");
            writer.open();
            writer.write(ca);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterEmptyChoreAssign.json");
            ca = reader.read();
            assertEquals("My chores", ca.getName());
            assertEquals(0, ca.getChores().size());
            assertEquals(0, ca.getPeople().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testJsonWriterGeneralChoreAssign() {
        ChoreAssign ca = new ChoreAssign("My chores");
        Chore chore1 = new Chore("dishes", "wash dishes", 15, Interval.DAILY);
        ca.addChore(chore1);
        Chore chore2 = new Chore("laundry", "wash laundry", 60, Interval.WEEKLY);
        ca.addChore(chore2);
        try {
            ca.addPerson("Joey");
            ca.addPerson("Chandler");
            ca.assignChore(chore1.getId(), "Joey");
            ca.assignChore(chore2.getId(), "Chandler");
            JsonWriter writer = new JsonWriter("./data/testJsonWriterGeneralChoreAssign.json");
            writer.open();
            writer.write(ca);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterGeneralChoreAssign.json");
            ca = reader.read();
            assertEquals("My chores", ca.getName());
            ArrayList<Chore> chores = ca.getChores();
            ArrayList<Chore> chores1 = new ArrayList<>();
            chores1.add(chores.get(0));
            ArrayList<Chore> chores2 = new ArrayList<>();
            chores2.add(chores.get(1));
            ArrayList<Person> people = ca.getPeople();
            assertEquals(2, chores.size());
            checkChore("dishes", "wash dishes", Interval.DAILY, 15, chores.get(0));
            checkChore("laundry", "wash laundry", Interval.WEEKLY, 60, chores.get(1));
            checkPerson("Joey", chores1, people.get(0));
            checkPerson("Chandler", chores2, people.get(1));
        } catch (PersonException e) {
        } catch (ChoreException e) {
        } catch (IOException e) {
        fail("Exception should not have been thrown");
        }
    }
}
