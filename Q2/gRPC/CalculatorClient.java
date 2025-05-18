import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;
        
        try (
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Conectado ao servidor da calculadora");
            
            // Exemplos automáticos
            System.out.println("Realizando cálculos de exemplo:");
            
            // Adição
            out.println("add:10:5");
            System.out.println("10 + 5 = " + in.readLine());
            
            // Subtração
            out.println("subtract:20:7");
            System.out.println("20 - 7 = " + in.readLine());
            
            // Multiplicação
            out.println("multiply:6:8");
            System.out.println("6 * 8 = " + in.readLine());
            
            // Divisão
            out.println("divide:100:4");
            System.out.println("100 / 4 = " + in.readLine());
            
            // Teste de erro - divisão por zero
            out.println("divide:10:0");
            System.out.println(in.readLine());
            
            // Modo interativo
            System.out.println("\nModo interativo (digite 'sair' para encerrar)");
            System.out.println("Formato: operação:numero1:numero2");
            System.out.println("Operações disponíveis: add, subtract, multiply, divide");
            
            String userInput;
            while (true) {
                System.out.print("> ");
                userInput = scanner.nextLine();
                
                if (userInput.equalsIgnoreCase("sair")) {
                    break;
                }
                
                out.println(userInput);
                System.out.println("Resultado: " + in.readLine());
            }
            
        } catch (IOException e) {
            System.out.println("Erro ao conectar ao servidor: " + e.getMessage());
        }
    }
}
