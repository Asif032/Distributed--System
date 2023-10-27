import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private static final int PORT = 8080;
  private static final int MAX_THREADS = 10;
  private static final List<ClientHandler> clients = new ArrayList<>();
  private static final ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(PORT);
      System.out.println("Chat server is running on port " + PORT);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Accepted connection from " + clientSocket.getInetAddress());


        ClientHandler clientHandler = new ClientHandler(clientSocket);
        clients.add(clientHandler);
        threadPool.submit(clientHandler);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public static void broadcast(String message) {
    for (ClientHandler client : clients) {
      client.sendMessage(message);
    }
  }
}