import java.net.*;
import java.util.Scanner;
import java.io.*;

class Client {
    public static void main(String args[]) {
        createClientConnection();
    }

    public static void createClientConnection() {
        Socket s = null;
        try {
            int serverPort = 25565;
            s = new Socket(InetAddress.getLocalHost(), serverPort);
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            DataInputStream in = new DataInputStream(s.getInputStream());
            Scanner scanner = new Scanner(System.in);
            String string;

            ClientConnection connection = new ClientConnection(s);
            while (true) {
                string = scanner.next();
                if (string.equals("exit")) {
                    s.close();
                    scanner.close();
                    return;
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage()); // host cannot be resolved
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage()); // end of stream reached
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage()); // error in reading the stream
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
        }
    }
}
