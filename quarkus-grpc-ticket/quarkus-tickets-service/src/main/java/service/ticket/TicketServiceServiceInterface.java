package service.ticket;

import io.grpc.stub.StreamObserver;

public interface TicketServiceServiceInterface {
    public void checkTicket(Ticket request, StreamObserver<Msp> responseObserver);

    public void changePrivateKey(PrivateKey request, StreamObserver<Confirmation> responseObserver);

    public void registerUser(User request, StreamObserver<Confirmation> responseObserver);
}
