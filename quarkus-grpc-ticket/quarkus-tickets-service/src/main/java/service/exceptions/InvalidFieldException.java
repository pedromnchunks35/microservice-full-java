package service.exceptions;

public class InvalidFieldException extends Exception {
    public InvalidFieldException(String name) {
        super("The given component is invalid: " + name);
    }
}
