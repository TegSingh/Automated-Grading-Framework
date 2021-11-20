package Client;

import java.io.*;
import java.util.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        // Socket active on port 3000
        try (Socket socket = new Socket("localhost", 3000)) {

            // Writer to send information to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Object to read from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Create an object of Scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;

            while (!"exit".equalsIgnoreCase(line)) {
                line = sc.nextLine();
                out.println(line);
                out.flush();

                // Display the server reply
                System.out.println("Server replied: " + in.readLine());
            }

            // Close the scanner object
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}