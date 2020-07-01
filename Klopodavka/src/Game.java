import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    boolean buttonClicked = false;
    boolean lock = false;
    private int step = 0;
    private int x, y;
    private JFrame frame = new JFrame(); //создает фрейм
    private JButton[][] grid; //объявляет кнопки
    private JPanel panel;
    private int width = 15;
    private int length = 15;
    private int value;

     Game() { //конструктор
        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridLayout(width, length));

        grid = new JButton[width][length]; //выделяет место в соответствии с размером сетки
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(i, j, 0); //создает новую кнопку
                grid[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        Cell c = (Cell) e.getSource();
                        x = c.getx();
                        y = c.gety();
                        value = c.getValue();
                        System.out.println(x + " " + y + " " + value + " " + step);
                        buttonClicked = true;
                    }
                });
                panel.add(grid[i][j]); //добавляет кнопки на сетку
            }
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); //устанавливает соответствующий размер фрейма
        frame.setVisible(true); //делает фрейм видимым
    }

     void createGame(int num) {
        if (num == 0) {
            ((Cell) grid[3][3]).setValue(1);
            ((Cell) grid[11][11]).setValue(-1);
        } else {
            ((Cell) grid[11][11]).setValue(1);
            ((Cell) grid[3][3]).setValue(-1);
        }
    }

     boolean checkButton() {

         if(nearbyKlops()){
             if (value == 0) {
                 ((Cell) grid[x][y]).setValue(1);
                 return true;
             } else if (value == -1) {
                 ((Cell) grid[x][y]).setValue(2);
                 return true;
             }
         }

         return false;
    }

    boolean buttonClicked(){
         return buttonClicked;
    }

    void releaseButtonClicked(){
         buttonClicked = false;
    }

    void updateMap(String data){
        String strArr[] = data.split(" ");

        int k = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                ((Cell)grid[i][j]).setValue(Integer.parseInt(strArr[k]));
                k++;
            }
        }
    }

     String getMap() {
        String data = "";
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                data += Integer.toString(((Cell) grid[i][j]).getValue());
                data += " ";
            }
        }

        return data;
    }

    int getStep(){
         return step;
    }

    void releaseStep(){
         step = 0;
    }

    void lockMap(){
        lock = true;
    }

    void unlockMap(){
         lock = false;
    }

    boolean isLock(){
         return lock;
    }

    void nextStep(){
         step++;
    }

    void winCheck(){
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if(((Cell) grid[i][j]).getValue() == -1){
                    return;
                }
            }
        }
        System.out.println("YOU ARE A WINNER");
    }

    boolean nearbyKlops(){
         for (int i = -1; i<2;++i){
             for(int j = -1; j<2;++j){
                 if(x+i >= 0 && x+i<=14 && y+j>=0 && y+j<=14){
                     if(((Cell)grid[x+i][y+j]).getValue() == 1 || ((Cell)grid[x+i][y+j]).isConnected()){
                         return true;
                     }
                 }
             }
         }
         return false;
    }

    void checkConnected(){

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if(((Cell) grid[i][j]).getValue() == 1){
                    ((Cell) grid[i][j]).Connected();
                }
                else {
                    ((Cell) grid[i][j]).unConnected();
                }
            }
        }


        for(int h = 0; h < 255; ++h) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < width; j++) {
                    if (((Cell) grid[i][j]).getValue() == 2) {
                        if (!((Cell) grid[i][j]).isConnected()) {

                            for (int k = -1; k < 2; ++k) {
                                for (int l = -1; l < 2; ++l) {

                                    if (i + k >= 0 && j + l >= 0 && i + k < 15 && j + l < 15) {
                                        if (((Cell) grid[i + k][j + l]).isConnected()) {
                                            ((Cell) grid[i][j]).Connected();
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}