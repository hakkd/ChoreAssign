package model.exceptions;

// Exception thrown when looking up ID that doesn't exist
public class IdNotFoundException extends ChoreException {

    public IdNotFoundException(String s) {
        super(s);
    }

}
