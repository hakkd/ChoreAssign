package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NoChoresExceptionTest extends ChoreExceptionTest {

    NoChoresException exception;

    @Test
    void testGetMessage() {
        exception = new NoChoresException("test");
        assertEquals("test", exception.getMessage());
    }
}
