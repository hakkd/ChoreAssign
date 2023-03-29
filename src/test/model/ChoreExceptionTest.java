package model;

import model.exceptions.ChoreException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChoreExceptionTest {

    private ChoreException exception;

    @Test
    void testGetMessage() {
        exception = new ChoreException("test message");
        assertEquals("test message", exception.getMessage());
    }
}
