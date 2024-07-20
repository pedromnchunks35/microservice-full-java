package org.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.acme.HelloGrpcClientStreamGrpc;
import org.acme.HelloReply;
import org.acme.HelloRequest;
import org.acme.HelloGrpcClientStreamGrpc.HelloGrpcClientStreamStub;
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
public class HelloGrpcClientStreamServiceTest {
    private static final Logger logger = Logger.getLogger(HelloGrpcClientStreamServiceTest.class.getName());
    private Server server;
    private ManagedChannel channel;
    private HelloGrpcClientStreamStub helloGrpcClientStreamStub;

    @BeforeEach
    public void setup() throws IOException {
        HelloGrpcClientStreamService service = new HelloGrpcClientStreamService();
        server = InProcessServerBuilder.forName("helloclientstream")
                .addService(service)
                .build();
        server.start();
        channel = InProcessChannelBuilder.forName("helloclientstream")
                .usePlaintext()
                .build();
        helloGrpcClientStreamStub = HelloGrpcClientStreamGrpc.newStub(channel);
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
    public void sendRequestTest() throws InterruptedException {
        AtomicReference<String> result = new AtomicReference<String>("");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<HelloReply> reply = new StreamObserver<HelloReply>() {
            String tempResult = "";

            @Override
            public void onNext(HelloReply value) {
                tempResult += value.getMessage();
            }

            @Override
            public void onError(Throwable t) {
                logger.warning("Something unexpected occured");
                assertEquals(true, false);
            }

            @Override
            public void onCompleted() {
                result.set(tempResult);
                finishLatch.countDown();
            }

        };
        StreamObserver<HelloRequest> req = helloGrpcClientStreamStub.sayHello(reply);
        for (int i = 0; i < 6; i++) {
            req.onNext(HelloRequest.newBuilder().setName("Pedro").build());
        }
        req.onCompleted();
        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            logger.warning("It did not catch the result within 1 minute");
        }
        assertEquals(result.get().contains("name=Pedro"), true);
    }
}
