package rest.exceptions;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String name){
        super("The "+name+" already exists");
    }
}
