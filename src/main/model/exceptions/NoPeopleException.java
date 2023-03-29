package model.exceptions;

// Exception thrown when trying to access list of Person when none have been created
public class NoPeopleException extends PersonException {

    public NoPeopleException(String s) {
        super(s);
    }
}
