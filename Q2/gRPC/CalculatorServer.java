package grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class CalculatorServer {

    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
            .addService(new CalculatorImpl())
            .build();

        server.start();
        System.out.println("Servidor gRPC iniciado.");
        server.awaitTermination();
    }

    static class CalculatorImpl extends CalculatorGrpc.CalculatorImplBase {
        public void add(CalcRequest req, StreamObserver<CalcResponse> res) {
            double result = req.getA() + req.getB();
            res.onNext(CalcResponse.newBuilder().setResult(result).build());
            res.onCompleted();
        }

        public void subtract(CalcRequest req, StreamObserver<CalcResponse> res) {
            double result = req.getA() - req.getB();
            res.onNext(CalcResponse.newBuilder().setResult(result).build());
            res.onCompleted();
        }

        public void multiply(CalcRequest req, StreamObserver<CalcResponse> res) {
            double result = req.getA() * req.getB();
            res.onNext(CalcResponse.newBuilder().setResult(result).build());
            res.onCompleted();
        }

        public void divide(CalcRequest req, StreamObserver<CalcResponse> res) {
            double result = req.getA() / req.getB();
            res.onNext(CalcResponse.newBuilder().setResult(result).build());
            res.onCompleted();
        }
    }
}