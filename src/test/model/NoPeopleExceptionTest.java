package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NoPeopleExceptionTest {

    NoPeopleException exception;

    @Test
    void testGetMessage() {
        exception = new NoPeopleException("test");
        assertEquals("test", exception.getMessage());
    }
}
