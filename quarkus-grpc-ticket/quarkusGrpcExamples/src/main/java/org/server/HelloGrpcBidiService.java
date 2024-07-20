package org.server;

import org.acme.HelloRequest;

import java.util.logging.Logger;

import org.acme.HelloGrpcBidiGrpc.HelloGrpcBidiImplBase;
import org.acme.HelloReply;

import io.grpc.stub.StreamObserver;

public class HelloGrpcBidiService extends HelloGrpcBidiImplBase {
    private static final Logger logger = Logger.getLogger(HelloGrpcBidiService.class.getName());

    @Override
    public StreamObserver<HelloRequest> sayHello(
            StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            String tempResult = "";

            @Override
            public void onNext(HelloRequest value) {
                tempResult += "name=" + value.getName();
            }

            @Override
            public void onError(Throwable t) {
                logger.warning("Something wrong happened");
            }

            @Override
            public void onCompleted() {
                for (int i = 0; i < 5; i++) {
                    responseObserver.onNext(HelloReply.newBuilder().setMessage(tempResult).build());
                }
                responseObserver.onCompleted();
            }
        };
    }
}
