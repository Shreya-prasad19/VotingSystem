import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    static Map<String, Integer> votes = new HashMap<>();
    static Vector<PrintWriter> clients = new Vector<>();

    public static void main(String[] args) throws Exception {

        votes.put("Java", 0);
        votes.put("Python", 0);
        votes.put("C++", 0);
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Server Started..");
new Thread(() -> {
    try {
        Thread.sleep(   12000); // 30 seconds
        System.out.println("Voting Ended!");

        for (PrintWriter client : clients) {
            client.println("Voting Closed!");
        }

        System.exit(0);

    } catch (Exception e) {}
}).start();
        while (true) {
            Socket socket = server.accept();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            clients.add(out);

            new ClientHandler(socket).start();
        }
    }

    static void broadcastResults() {
        String result = "\n=== LIVE RESULTS ===\n";

for (String key : votes.keySet()) {
    int count = votes.get(key);

    result += key + " : " + count + "  ";
    for(int i=0; i<count; i++) result += "*";
    result += "\n";
}
    }
}

class ClientHandler extends Thread {
    Socket socket;
    BufferedReader in;

    ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        try {
            String vote;
while ((vote = in.readLine()) != null) {

    String[] parts = vote.split(":");
    String user = parts[0];
    String option = parts[1];

    synchronized (Server.votes) {
        Server.votes.put(option, Server.votes.get(option) + 1);
    }

    System.out.println(user + " voted for " + option);

    Server.broadcastResults();
}
        } catch (Exception e) {
            System.out.println("Client disconnected");
        }
    }
}