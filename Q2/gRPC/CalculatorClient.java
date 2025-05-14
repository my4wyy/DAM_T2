package grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build();

        CalculatorGrpc.CalculatorBlockingStub stub = CalculatorGrpc.newBlockingStub(channel);

        CalcRequest request = CalcRequest.newBuilder().setA(10).setB(5).build();

        System.out.println("Soma: " + stub.add(request).getResult());
        System.out.println("Multiplicação: " + stub.multiply(request).getResult());

        channel.shutdown();
    }
}