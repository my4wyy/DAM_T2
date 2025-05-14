package rmi;

import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            Calculator calc = (Calculator) Naming.lookup("rmi://localhost:1099/CalculatorService");
            System.out.println("Soma: " + calc.add(10, 5));
            System.out.println("Subtração: " + calc.subtract(10, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}