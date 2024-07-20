# Info
- This is a grpc quarkus service
- In order to construct protofiles we must change the proto file and then use `mvn compile`
- In order to make this happen and since the schematics are different from the normal java gRPC we will create examples for:
  - single-single
  - single-stream
  - stream-single
  - stream-stream
- Basicly we will not use the grpc functionalities because the original ones are better
- To come up with a solution that we could test we simply use the testing library from the grpc and the rest is the same