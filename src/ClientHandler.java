import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
  private final Socket clientSocket;
  private PrintWriter out;

  public ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  @Override
  public void run() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      out = new PrintWriter(clientSocket.getOutputStream(), true);


      String message;
      while ((message = in.readLine()) != null) {
        System.out.println("Received from " + clientSocket.getInetAddress() + ": " + message);


        Server.broadcast(message);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        clientSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void sendMessage(String message) {
    out.println(message);
  }
}
