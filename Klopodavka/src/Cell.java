import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {
    private int x, y;
    private int value;
    boolean connected = false;

    Cell(int x, int y, int value){
        this.x = x;
        this.y = y;

        switch (value){
            case -2: this.setBackground(new Color(139,0,0)); break;
            case -1: this.setBackground(Color.red); break;
            case 0: this.setBackground(Color.lightGray); break;
            case 1: this.setBackground(Color.blue); connected = true; break;
            case 2: this.setBackground(new Color(0,0,139)); break;
        }
    }

    public void setValue(int value){
        this.value = value;

        switch (value){
            case -2: this.setBackground(new Color(139,0,0)); break;
            case -1: this.setBackground(Color.red); break;
            case 0: this.setBackground(Color.lightGray); break;
            case 1: this.setBackground(Color.blue); connected = true; break;
            case 2: this.setBackground(new Color(0,0,139)); break;
        }
    }

    public int getValue(){
        return value;
    }
    public boolean isConnected(){
        return connected;
    }

    public void Connected(){
        connected = true;
    }

    public void unConnected(){
        connected = false;
    }
    public int getx(){
        return x;
    }
    public int gety(){
        return y;
    }

}
