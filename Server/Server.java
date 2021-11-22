package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(3000);
            server.setReuseAddress(true);

            while (true) {
                // Socket object to connect incoming clients
                Socket client = server.accept();
                // Print the information about the new client
                System.out.println("New client connected: " + client.getInetAddress().getHostAddress().toString());

                // Create a client handler object
                ClientHandler clientHandler = new ClientHandler(client);
                new Thread(clientHandler).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        // Override the runnable run() method
        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                // Initialize the object
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                // Get the input stream
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(
                            "Client " + clientSocket.getInetAddress().getHostAddress().toString() + ": " + line);
                    out.println(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
