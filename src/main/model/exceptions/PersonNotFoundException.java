package model.exceptions;

// Exception thrown when trying to find person that doesn't exist
public class PersonNotFoundException extends PersonException {
    public PersonNotFoundException(String s) {
        super(s);
    }
}
