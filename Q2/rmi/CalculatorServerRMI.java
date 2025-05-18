import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServerRMI extends UnicastRemoteObject implements CalculatorRMI {
    protected CalculatorServerRMI() throws RemoteException {
        super(); // Construtor necessário para UnicastRemoteObject
    }

    @Override
    public double add(double a, double b) throws RemoteException {
        System.out.println("Servidor RMI: Recebida requisição de soma: " + a + " + " + b);
        return a + b;
    }

    @Override
    public double subtract(double a, double b) throws RemoteException {
        System.out.println("Servidor RMI: Recebida requisição de subtração: " + a + " - " + b);
        return a - b;
    }

    @Override
    public double multiply(double a, double b) throws RemoteException {
        System.out.println("Servidor RMI: Recebida requisição de multiplicação: " + a + " * " + b);
        return a * b;
    }

    @Override
    public double divide(double a, double b) throws RemoteException, IllegalArgumentException {
        System.out.println("Servidor RMI: Recebida requisição de divisão: " + a + " / " + b);
        if (b == 0) {
            throw new IllegalArgumentException("Divisão por zero não permitida.");
        }
        return a / b;
    }

    public static void main(String[] args) {
        try {
            CalculatorServerRMI server = new CalculatorServerRMI();
            // Cria ou localiza o RMI Registry na porta 1099 (padrão)
            Registry registry = LocateRegistry.createRegistry(1099);
            // Registra o objeto remoto com um nome ("CalculatorService")
            registry.rebind("CalculatorService", server);
            System.out.println("Servidor da Calculadora RMI está em execução...");
        } catch (Exception e) {
            System.err.println("Exceção no servidor RMI: " + e.toString());
            e.printStackTrace();
        }
    }
}

