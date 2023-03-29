package model.exceptions;

// Exception thrown when trying to access chores, but none have been created
public class NoChoresException extends ChoreException {

    public NoChoresException(String s) {
        super(s);
    }
}
