package org.server;

import org.acme.HelloReply;
import org.acme.HelloRequest;
import org.acme.HelloGrpcServerStreamGrpc.HelloGrpcServerStreamImplBase;

import io.grpc.stub.StreamObserver;

public class HelloGrpcServerStreamService extends HelloGrpcServerStreamImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> reply) {
        String name = request.getName();
        for (int i = 0; i < 5; i++) {
            reply.onNext(HelloReply.newBuilder().setMessage("name=" + name).build());
        }
        reply.onCompleted();
    }
}
