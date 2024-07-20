package org.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.acme.HelloGrpcBidiGrpc;
import org.acme.HelloGrpcClientStreamGrpc;
import org.acme.HelloGrpcBidiGrpc.HelloGrpcBidiBlockingStub;
import org.acme.HelloGrpcBidiGrpc.HelloGrpcBidiStub;
import org.acme.HelloGrpcClientStreamGrpc.HelloGrpcClientStreamStub;
import org.acme.HelloReply;
import org.acme.HelloRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class HelloGrpcBidiServiceTest {
    private static final Logger logger = Logger.getLogger(HelloGrpcClientStreamServiceTest.class.getName());
    private Server server;
    private ManagedChannel channel;
    private HelloGrpcBidiStub helloGrpcBidiStub;

    @BeforeEach
    public void setup() throws IOException {
        HelloGrpcBidiService service = new HelloGrpcBidiService();
        server = InProcessServerBuilder.forName("helloclientstream")
                .addService(service)
                .build();
        server.start();
        channel = InProcessChannelBuilder.forName("helloclientstream")
                .usePlaintext()
                .build();
        helloGrpcBidiStub = HelloGrpcBidiGrpc.newStub(channel);
    }

    @Test
    public void sendingStreamTest() throws InterruptedException {

    }

    @AfterEach
    public void tearDown() {
        channel.shutdown();
        server.shutdown();
    }

    @Test
    public void sendBidiMessagesTest() throws InterruptedException {
        AtomicReference<String> reference = new AtomicReference<String>("");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<HelloReply> reply = new StreamObserver<HelloReply>() {
            String tempVar = "";

            @Override
            public void onNext(HelloReply value) {
                tempVar += value.getMessage();
            }

            @Override
            public void onError(Throwable t) {
                logger.warning("Something unexpected happened");
                assertEquals(false, true);
            }

            @Override
            public void onCompleted() {
                reference.set(tempVar);
                finishLatch.countDown();
            }

        };
        StreamObserver<HelloRequest> request = helloGrpcBidiStub.sayHello(reply);
        for (int i = 0; i < 5; i++) {
            request.onNext(HelloRequest.newBuilder().setName("Pedro").build());
        }
        request.onCompleted();
        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            logger.warning("It did not catch the result within 1 minute");
        }
        assertEquals(reference.get().contains("Pedro"), true);
    }
}
