package model;

public class PersonException extends Exception {
    private String message;

    public PersonException(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }
}
