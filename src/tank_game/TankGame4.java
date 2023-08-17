package tank_game;

import javax.swing.*;

public class TankGame4 extends JFrame {

    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame4 game = new TankGame4();
    }

    public TankGame4(){
        mp = new MyPanel();

        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
