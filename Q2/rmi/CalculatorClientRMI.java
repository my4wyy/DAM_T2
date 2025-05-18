import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClientRMI {
    public static void main(String[] args) {
        String host = (args.length < 1) ? "localhost" : args[0];
        try {
            // Localiza o RMI Registry no host e porta especificados
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            // Obtém o stub do objeto remoto pelo nome registrado
            CalculatorRMI calculator = (CalculatorRMI) registry.lookup("CalculatorService");

            System.out.println("Cliente RMI realizando cálculos...");
            System.out.println("10 + 5 = " + calculator.add(10, 5));
            System.out.println("20 - 7 = " + calculator.subtract(20, 7));
            System.out.println("6 * 8 = " + calculator.multiply(6, 8));
            System.out.println("100 / 4 = " + calculator.divide(100, 4));

            // Testando a divisão por zero
            try {
                System.out.println("10 / 0 = " + calculator.divide(10, 0));
            } catch (IllegalArgumentException e) {
                System.err.println("Erro na divisão: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Exceção no cliente RMI: " + e.toString());
            e.printStackTrace();
        }
    }
}

