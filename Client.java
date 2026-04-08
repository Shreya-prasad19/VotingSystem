import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {

        BufferedReader userInput = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.print("Enter your name: ");
        String name = userInput.readLine();

        Socket socket = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

        // Receive results
        new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (Exception e) {}
        }).start();

        System.out.println("1. Java\n2. Python\n3. C++");

        while (true) {
            String choice = userInput.readLine();

            switch (choice) {
                case "1": out.println(name + ":Java"); break;
                case "2": out.println(name + ":Python"); break;
                case "3": out.println(name + ":C++"); break;
            }
        }
    }
}