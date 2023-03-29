package model.exceptions;

// Exception for when two people have the same name
public class DuplicatePersonException extends PersonException {
    public DuplicatePersonException(String s) {
        super(s);
    }
}
