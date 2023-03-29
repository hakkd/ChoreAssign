package model.exceptions;

// Exception for chore
public class ChoreException extends Exception {
    private String message;

    public ChoreException(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }
}
