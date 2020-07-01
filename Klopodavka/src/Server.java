import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


class Server {
    private static final ClientHandler[] clients = new ClientHandler[2];
    private static int connectionNum = 0;
    public static void main (String args[]) {
        try {
            int serverPort = 25565; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort); // new server port generated
            int k = 0;
            while(connectionNum != 2) {
                Socket clientSocket = listenSocket.accept(); // listen for new connection
                System.out.println("Client connected");
                ClientHandler c = new ClientHandler(clientSocket, connectionNum++); // launch new thread
                synchronized (clients) {
                    clients[k] = c;
                }
                k++;
            }

            while(true) {
                Thread.sleep(10);
                if(clients[0].isMsg()){
                    clients[0].reciveMsg();
                    clients[1].setMap(clients[0].getMap());
                    clients[1].sendMap();
                }
                if(clients[1].isMsg()){
                    clients[1].reciveMsg();
                    clients[0].setMap(clients[1].getMap());
                    clients[0].sendMap();

                }
            }

        } catch(IOException e) { System.out.println("Listen socket:"+e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
// писть в клиете сообщения жать энтр и они печатают на сервере