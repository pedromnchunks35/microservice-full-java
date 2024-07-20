package org.server;

import org.acme.HelloRequest;
import org.acme.HelloGrpcClientStreamGrpc.HelloGrpcClientStreamImplBase;
import org.acme.HelloReply;

import io.grpc.stub.StreamObserver;
import java.util.logging.Logger;

public class HelloGrpcClientStreamService extends HelloGrpcClientStreamImplBase {
    private static final Logger logger = Logger.getLogger(HelloGrpcClientStreamService.class.getName());

    @Override
    public StreamObserver<HelloRequest> sayHello(StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            String valueResult = "";

            @Override
            public void onNext(HelloRequest value) {
                valueResult += ("name=" + value.getName());
            }

            @Override
            public void onError(Throwable t) {
                logger.warning("Some error occured");
            }

            @Override
            public void onCompleted() {
                HelloReply response = HelloReply.newBuilder()
                        .setMessage(valueResult)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }

        };
    }
}
