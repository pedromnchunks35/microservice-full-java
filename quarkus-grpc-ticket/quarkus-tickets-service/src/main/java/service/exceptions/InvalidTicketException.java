package service.exceptions;

public class InvalidTicketException extends Exception {
    public InvalidTicketException(String reason) {
        super("Ticket is invalid because: " + reason);
    }
}
