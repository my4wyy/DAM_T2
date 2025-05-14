package rmi;

import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            Calculator calc = new CalculatorImpl();
            Naming.rebind("rmi://localhost:1099/CalculatorService", calc);
            System.out.println("Servidor RMI pronto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}