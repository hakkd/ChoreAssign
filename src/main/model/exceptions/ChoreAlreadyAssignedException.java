package model.exceptions;

// Exception for when a chore is already assigned (can't be assigned twice)
public class ChoreAlreadyAssignedException extends ChoreException {

    public ChoreAlreadyAssignedException(String s) {
        super(s);
    }
}
