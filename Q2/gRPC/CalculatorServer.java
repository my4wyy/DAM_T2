import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class CalculatorServer {
    public static void main(String[] args) {
        int port = 8080;
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado na porta " + port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                
                // Cria uma thread para lidar com o cliente
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
    
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String[] parts = inputLine.split(":");
                    if (parts.length != 3) {
                        out.println("Formato inválido. Use: operação:numero1:numero2");
                        continue;
                    }
                    
                    String operation = parts[0];
                    double num1 = Double.parseDouble(parts[1]);
                    double num2 = Double.parseDouble(parts[2]);
                    double result = 0;
                    
                    switch (operation) {
                        case "add":
                            result = num1 + num2;
                            break;
                        case "subtract":
                            result = num1 - num2;
                            break;
                        case "multiply":
                            result = num1 * num2;
                            break;
                        case "divide":
                            if (num2 == 0) {
                                out.println("Erro: Divisão por zero");
                                continue;
                            }
                            result = num1 / num2;
                            break;
                        default:
                            out.println("Operação inválida. Use: add, subtract, multiply ou divide");
                            continue;
                    }
                    
                    out.println(result);
                }
                
                clientSocket.close();
                System.out.println("Cliente desconectado");
                
            } catch (IOException e) {
                System.out.println("Erro ao lidar com cliente: " + e.getMessage());
            }
        }
    }
}
