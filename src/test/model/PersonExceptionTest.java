package model;

import model.exceptions.PersonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonExceptionTest {

    private PersonException exception;

    @Test
    void testGetMessage() {
        exception = new PersonException("test message");
        assertEquals("test message", exception.getMessage());
    }
}
