import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClientGUI {

    static PrintWriter out;
    static JTextArea area;

    public static void main(String[] args) throws Exception {

        String name = JOptionPane.showInputDialog("Enter your name:");

        Socket socket = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        JFrame frame = new JFrame("Voting System");
        frame.setSize(400, 400);

        JButton b1 = new JButton("Java");
        JButton b2 = new JButton("Python");
        JButton b3 = new JButton("C++");

        area = new JTextArea();

        b1.addActionListener(e -> out.println(name + ":Java"));
        b2.addActionListener(e -> out.println(name + ":Python"));
        b3.addActionListener(e -> out.println(name + ":C++"));

        frame.setLayout(new FlowLayout());
        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.add(new JScrollPane(area));

        frame.setVisible(true);

        // Receive updates
        new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    area.setText(msg);
                }
            } catch (Exception e) {}
        }).start();
    }
}