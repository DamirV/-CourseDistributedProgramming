import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class ClientConnection extends Thread {
    int connectionNum;
    DataInputStream in;
    DataOutputStream out;
    Socket serverSocket;
    BufferedReader br;
    Scanner scanner;
    Game game;


    public ClientConnection(Socket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            br = new BufferedReader(new InputStreamReader(System.in));
            in = new DataInputStream(serverSocket.getInputStream());
            out = new DataOutputStream(serverSocket.getOutputStream());
            scanner = new Scanner(System.in);
            game = new Game();
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ClientConnection.this.start();
                }
            });
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            Thread.sleep(1000);
            String data = in.readUTF();
            System.out.println(data);
            connectionNum = Integer.parseInt(data);
            System.out.println("NUM was received");
            game.createGame(Integer.parseInt(data));

            if(connectionNum == 1){
                game.lockMap();
                data = in.readUTF();
                game.updateMap(data);
                System.out.println("map updated");
                game.unlockMap();
                game.checkConnected();
            }

            while (true) {

                Thread.sleep(10);

                if(game.buttonClicked() && (!game.isLock())){
                    if(game.checkButton()){
                        game.nextStep();
                        game.checkConnected();
                        System.out.println("connected checked");
                    }
                    game.releaseButtonClicked();
                }

                if(game.getStep() == 4){
                    game.releaseStep();
                    game.lockMap();
                    game.winCheck();
                    data = game.getMap();
                    out.writeUTF(data);
                    out.flush();

                    System.out.println("msg send");
                    data = in.readUTF();
                    game.updateMap(data);
                    System.out.println("map updated");
                    game.unlockMap();
                    game.checkConnected();
                    System.out.println("connected checked");
                }
            }
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}