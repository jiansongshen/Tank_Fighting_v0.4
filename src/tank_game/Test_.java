package tank_game;

import javax.swing.*;
import java.awt.*;

public class Test_ extends JFrame {
    TestClass tc = null;
    public static void main(String[] args) {
        Test_ test = new Test_();
    }

    public Test_(){
        this.tc = new TestClass();
        this.add(tc);
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}


class TestClass extends JPanel{
    @Override
    public void paint(Graphics g) {
        System.out.println("draw");
        Image image_testclass  = Toolkit.getDefaultToolkit().getImage(TestClass.class.getResource("/explode_1.png"));
        g.drawImage(image_testclass,10,10,this);
    }
}