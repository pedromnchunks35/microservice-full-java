package service.ticket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.protobuf.ByteString;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import service.caops.CaOps;
import service.exceptions.InvalidFieldException;
import service.ticket.TicketServiceGrpc.TicketServiceImplBase;
import service.ticketoperations.TicketOperations;

@GrpcService
public class TicketServiceService extends TicketServiceImplBase implements TicketServiceServiceInterface {

    @Override
    public void changePrivateKey(PrivateKey request, StreamObserver<Confirmation> responseObserver) {
        try {
            TicketOperations.changePrivateKey(request.getPrivateKey(), Path.of("./src/main/java/service/keypairs/"));
        } catch (Exception e) {
            responseObserver.onError(e);
            return;
        }
        responseObserver.onNext(Confirmation.newBuilder().setCode(201).setMessage("Success").build());
    }

    @Override
    public void checkTicket(Ticket request, StreamObserver<Msp> responseObserver) {
        try {
            TicketOperations.isOkToEnroll(request, Path.of("./src/main/java/service/keypairs/key.pem"));
        } catch (Exception e) {
            responseObserver.onError(e);
            return;
        }
        User user = User.newBuilder().setUsername(request.getUsername()).setPassword("12341234").build();
        try {
            CaOps.enrollUserMsp(user, "./src/main/java/service/caops");
        } catch (IOException e) {
            responseObserver.onError(e);
            return;
        }
        try {
            byte[] cacert = CaOps.getFileInSomeDirectory("./src/main/java/service/caops/tempMsp/cacerts");
            byte[] keystore = CaOps.getFileInSomeDirectory("./src/main/java/service/caops/tempMsp/keystore");
            byte[] signcert = CaOps.getFileInSomeDirectory("./src/main/java/service/caops/tempMsp/signcerts");
            byte[] userFile = CaOps.getFileInSomeDirectory("./src/main/java/service/caops/tempMsp/user");
            byte[] issuerPublicKey = Files
                    .readAllBytes(Path.of("./src/main/java/service/caops/tempMsp/IssuerPublicKey"));
            byte[] issuerRevocationPublicKey = Files
                    .readAllBytes(Path.of("./src/main/java/service/caops/tempMsp/IssuerRevocationPublicKey"));
            responseObserver.onNext(Msp.newBuilder()
                    .setCacert(ByteString.copyFrom(cacert))
                    .setKeystore(ByteString.copyFrom(keystore))
                    .setSigncert(ByteString.copyFrom(signcert))
                    .setUser(ByteString.copyFrom(userFile))
                    .setIssuerPublicKey(ByteString.copyFrom(issuerPublicKey))
                    .setIssuerRevocationPublicKey(ByteString.copyFrom(issuerRevocationPublicKey))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
            return;
        }
    }

    @Override
    public void registerUser(User request, StreamObserver<Confirmation> responseObserver) {
        try {
            CaOps.checkUser(request);
        } catch (InvalidFieldException e) {
            responseObserver.onError(e);
            return;
        }
        try {
            CaOps.registerUser(request, "./src/main/java/service/caops");
        } catch (IOException e) {
            responseObserver.onError(e);
            return;
        }
        responseObserver.onNext(Confirmation.newBuilder().setCode(201).setMessage("Success").build());
    }
}
