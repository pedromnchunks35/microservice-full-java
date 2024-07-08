package rest.exceptions;

public class DoesNotExistException extends Exception {
    public DoesNotExistException(String name) {
        super("The " + name + " does not exist");
    }
}
