package service.ticket;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.quarkus.test.junit.QuarkusTest;
import service.ticket.TicketServiceGrpc.TicketServiceBlockingStub;

import java.util.logging.Logger;
@QuarkusTest
public class TicketServiceServiceTest {
    private static final Logger logger = Logger.getLogger(TicketServiceServiceTest.class.getName());
    private Server server;
    private ManagedChannel channel;
    private TicketServiceService service;
    private TicketServiceBlockingStub blockingStub;

    @BeforeEach
    public void setup() throws IOException {
        service = new TicketServiceService();
        server = InProcessServerBuilder.forName("simplechannel")
                .addService(service)
                .build();
        server.start();
        channel = InProcessChannelBuilder.forName("simplechannel")
                .usePlaintext()
                .build();
        blockingStub = TicketServiceGrpc.newBlockingStub(channel);
    }

    @AfterEach
    public void tearDown() {
        channel.shutdown();
        server.shutdown();
    }

    @Test
    public void checkTicketTest() {
        // ? Read a ticket
        // ? send a ticket to ckeck the ticket
        // ? receive msp
        // ? store msp
        // ? verify msp
        // ? clean msp
    }
    @Test
    public void changePrivateKey(){
        //? Read private key
        //? Send the private key
        //? Receive confirmation
        //? Checkout the codes that come from it
        //? Clean everything
    }
}
