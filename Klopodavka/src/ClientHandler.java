import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    int connectionNum;
    private boolean running = true;
    boolean msg = false;
    int[][] map;

    public ClientHandler(Socket clientSocket, int connectionNum) throws IOException {
        map = new int[15][15];
        this.clientSocket = clientSocket;
        this.connectionNum = connectionNum;
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("All created");
        this.start();
    }

    int[][] getMap(){
        return map;
    }

    void reciveMsg(){
        msg = false;
    }

    public boolean isMsg() {
        return msg;
    }

    void setMap(int[][] map){
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                this.map[i][j] = map[i][j];
            }
        }
    }

    void sendMap() throws IOException {
        String data = "";
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                data += Integer.toString(map[i][j]);
                data += " ";
            }
        }
        out.writeUTF(data);
        out.flush();
    }

    @Override
    public void run() {
        try {
            out.writeUTF(Integer.toString(connectionNum));
            out.flush();
            System.out.println("msg send");
            while (running) {
                String data = in.readUTF(); // read a line of data from the stream
                System.out.println("map was received");
                String strArr[] = data.split(" ");

                int k = 0;
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 15; j++) {
                        map[i][j] = -1 * Integer.parseInt(strArr[k]);
                        k++;
                    }
                }

                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 15; j++) {
                        System.out.print(map[i][j]);
                        System.out.print(" ");
                    }
                    System.out.println();
                }

                msg = true;
                Thread.sleep(500);

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}