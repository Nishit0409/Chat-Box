import java.net.*;
import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Client extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    // declare components
    public JLabel heading = new JLabel("Nishit Agarwal");
    public JTextArea messageArea = new JTextArea();
    public JTextField messageInput = new JTextField();
    public Font font = new Font("Roboto", Font.BOLD, 20);

    // constructor
    public Client() {
        try {
            System.out.println("Sending request to Server...");
            socket = new Socket("192.168.29.68", 6666);
            System.out.println("Connection Established....!");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            createGUI();
            handleEvents();
            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createGUI() {
        // GUI code
        this.setTitle("Client Messager");
        this.setSize(600, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // coding for component

        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);
        heading.setIcon(new ImageIcon("IMG_3719.JPG"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
        // To take Client area to the Center of the line
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        // To create space under Client Area
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // setting frame layout
        this.setLayout(new BorderLayout());
        // adding components to frame
        this.add(heading, BorderLayout.NORTH);
        this.add(messageArea, BorderLayout.CENTER);
        this.add(messageInput, BorderLayout.SOUTH);

        this.setVisible(true);

    }

    // method for handling events
    public void handleEvents() {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

                // System.out.println("key released: "+e.getKeyCode());
                if (e.getKeyCode() == 10) {
                    String contentToSend = messageInput.getText();
                    messageArea.append("Me: " + contentToSend + "\n");
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                }

            }

        });

    }

    // method to start reading
    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("Reader Started!");
            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Shubham Terminated the chat !!!");
                        JOptionPane.showMessageDialog(this, "Shubham Terminated the chat !!!");
                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }
                    System.out.println("Shubham: " + msg);
                    messageArea.append("Shubham: " + msg + "\n");
                }

            }

            catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(r1).start();

    }

    // method to start writing
    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writer started!");
            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        new Thread(r2).start();

    }

    public static void main(String args[]) {
        System.out.println("This is Client!");
        new Client();
    }

}