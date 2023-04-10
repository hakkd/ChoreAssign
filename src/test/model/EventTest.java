package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Adapted from CPSC210 AlarmSystem
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e;
	private Date d;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
	@BeforeEach
	public void runBefore() {
		e = new Event("Created new chore");   // (1)
		d = Calendar.getInstance().getTime();   // (2)
	}
	
	@Test
	public void testEvent() {
		assertEquals("Created new chore", e.getDescription());
		assertEquals(d, e.getDate());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Created new chore", e.toString());
	}

    @Test
    public void testHashCode() {
        Event e2 = e;
        assertTrue(e.hashCode() == e2.hashCode());
    }

    @Test
    public void testEquals() {
        Event e2 = e;
        assertEquals(e, e2);
        assertFalse(e.equals(null));
        assertFalse(e.equals(new Person("a")));
    }
}
