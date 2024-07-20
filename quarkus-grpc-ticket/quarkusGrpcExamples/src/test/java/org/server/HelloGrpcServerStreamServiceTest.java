package org.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.acme.HelloGrpcClientStreamGrpc;
import org.acme.HelloGrpcServerStreamGrpc;
import org.acme.HelloRequest;
import org.acme.HelloGrpcClientStreamGrpc.HelloGrpcClientStreamStub;
import org.acme.HelloGrpcServerStreamGrpc.HelloGrpcServerStreamBlockingStub;
import org.acme.HelloReply;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class HelloGrpcServerStreamServiceTest {
    private static final Logger logger = Logger.getLogger(HelloGrpcClientStreamServiceTest.class.getName());
    private Server server;
    private ManagedChannel channel;
    private HelloGrpcServerStreamBlockingStub blockStub;

    @BeforeEach
    public void setup() throws IOException {
        HelloGrpcServerStreamService service = new HelloGrpcServerStreamService();
        server = InProcessServerBuilder.forName("helloclientstream")
                .addService(service)
                .build();
        server.start();
        channel = InProcessChannelBuilder.forName("helloclientstream")
                .usePlaintext()
                .build();
        blockStub = HelloGrpcServerStreamGrpc.newBlockingStub(channel);
    }

    @AfterEach
    public void tearDown() {
        channel.shutdown();
        server.shutdown();
    }

    @Test
    public void sendMessageTest() {
        Iterator<HelloReply> reply = blockStub.sayHello(HelloRequest.newBuilder().setName("Pedro").build());
        int numberOfMessages = 0;
        String msg = "";
        while (reply.hasNext()) {
            numberOfMessages++;
            HelloReply replyObj = reply.next();
            msg += replyObj.getMessage();
        }
        assertEquals(numberOfMessages, 5);
        assertEquals(msg.contains("name=Pedro"), true);
    }
}
