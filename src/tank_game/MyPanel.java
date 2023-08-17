package tank_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

//实现Runnable接口，使其能不断repaint
//实现KeyListener接口，使其能接收用户对键盘的输入
public class MyPanel extends JPanel implements KeyListener, Runnable {
    MyTank myTank = null;
    //敌人坦克列表
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //敌人坦克数量
    int enemy_tank_size = 3;

    Image image_explode_1 = null;

    Vector<Explode> explodeVector = null;

    public MyPanel() {
        //图片加载
        image_explode_1 = Toolkit.getDefaultToolkit().getImage(TestClass.class.getResource("/explode_1.png"));

        //炸弹集合生成
        explodeVector = new Vector<>();

        //我方坦克生成
        myTank = new MyTank(800, 500, 1000, 750, 0);

        //敌方坦克生成
        for (int i = 0; i < enemy_tank_size; i++) {
            //位置确定
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 30,
                    1000, 750, 1);
            enemyTank.setDirect(3);
            //为敌方坦克添加子弹
            Bullet bullet = new Bullet(enemyTank.getX(), enemyTank.getY(),
                    enemyTank.getSpeed() + 20, enemyTank.getDirect(), 1);
            bullet.start();
            enemyTank.getBulletVector().add(bullet);

            //new Thread(enemyTank.getBulletVector().lastElement()).start();
            //添加新建的坦克到敌方数组中
            enemyTanks.add(enemyTank);

        }

        //坦克，起洞！
        for (int i = 0; i < enemyTanks.size(); i++) {
            new Thread(enemyTanks.get(i)).start();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //设置速度
        myTank.setSpeed(8);
        //绘制我方坦克
        if (myTank.isLive()) {
            drawTank(myTank, g, myTank.getDirect(), 0);
        }

        //绘制我方的子弹
        for (int i = 0; i < myTank.getBulletVector().size(); i++) {
            //获取子弹对象
            Bullet bullet_temp = myTank.getBulletVector().get(i);

            if (bullet_temp != null && bullet_temp.isLive()) {
                g.fill3DRect(bullet_temp.getX() - (myTank.getX_frame() * 2 / 1000),
                        bullet_temp.getY() - (myTank.getX_frame() * 2 / 1000),
                        (myTank.getX_frame() * 4 / 1000),
                        (myTank.getX_frame() * 4 / 1000),
                        false);
            }

            if (!bullet_temp.isLive()) {
                myTank.getBulletVector().remove(bullet_temp);
            }
        }

        //绘制敌方的坦克,子弹
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //还活着就绘制
            if (enemyTank.isLive()) {
                drawTank(enemyTank, g, enemyTank.getDirect(), 1);

                //绘制子弹
                for (int j = 0; j < enemyTank.getBulletVector().size(); j++) {
                    Bullet bullet = enemyTank.getBulletVector().get(j);
                    //判断是否绘制子弹
                    if (bullet.isLive()) {
                        //bullet.start();
                        g.fill3DRect(bullet.getX(), bullet.getY(),
                                myTank.getX_frame() * 4 / 1000,
                                myTank.getX_frame() * 4 / 1000,
                                false);
                    } else {
                        enemyTank.getBulletVector().remove(bullet);
                    }
                }
            }


        }

        //绘制爆炸效果
        for (int i = 0; i < explodeVector.size(); i++) {
            Explode explode_temp = explodeVector.get(i);

            //根据生命值绘画图片
            if (explode_temp.getLifetime() > 6) {
                g.drawImage(image_explode_1, explode_temp.getX() - 10, explode_temp.getY() - 10, 20, 20, this);
            } else if (explode_temp.getLifetime() > 3) {
                g.drawImage(image_explode_1, explode_temp.getX() - 20, explode_temp.getY() - 20, 40, 40, this);
            } else if (explode_temp.getLifetime() > 0) {
                g.drawImage(image_explode_1, explode_temp.getX() - 30, explode_temp.getY() - 30, 60, 60, this);
            } else {
                explodeVector.remove(explode_temp);
            }
            //爆炸时间减少
            explode_temp.explode();
        }
    }

    /**
     * @param tank     坦克
     * @param graphics 画笔
     * @param direct   方向（上：1， 右：2， 下：3， 左：4）
     * @param type     坦克类型
     */
    //绘画坦克
    public void drawTank(Tank tank, Graphics graphics, int direct, int type) {
        //确定绘画坦克的颜色
        switch (type) {
            case 0:
                graphics.setColor(Color.RED);
                break;
            case 1:
                graphics.setColor(Color.orange);
                break;
        }

        //判断方向绘画坦克
        switch (direct) {
            case 1:
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 2 / 100), tank.getY() - (tank.getX_frame() * 3 / 100), tank.getX_frame() / 100, tank.getX_frame() * 6 / 100, false);
                graphics.fill3DRect(tank.getX() + (tank.getX_frame() / 100), tank.getY() - (tank.getX_frame() * 3 / 100), tank.getX_frame() / 100, tank.getX_frame() * 6 / 100, false);
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() / 100), tank.getY() - (tank.getX_frame() * 2 / 100), tank.getX_frame() * 2 / 100, tank.getX_frame() * 4 / 100, false);
                graphics.fillOval(tank.getX() - (tank.getX_frame() / 100), tank.getY() - (tank.getX_frame() / 100), tank.getX_frame() * 2 / 100, tank.getX_frame() * 2 / 100);
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 2 / 1000), tank.getY() - (tank.getX_frame() * 5 / 100), tank.getX_frame() * 4 / 1000, tank.getX_frame() * 4 / 100, false);
                break;
            case 2:
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 3 / 100), tank.getY() - (tank.getX_frame() * 2 / 100), tank.getX_frame() * 6 / 100, tank.getX_frame() / 100, false);
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 3 / 100), tank.getY() + (tank.getX_frame() / 100), tank.getX_frame() * 6 / 100, tank.getX_frame() / 100, false);
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 2 / 100), tank.getY() - (tank.getX_frame() / 100), tank.getX_frame() * 4 / 100, tank.getX_frame() * 2 / 100, false);
                graphics.fillOval(tank.getX() - (tank.getX_frame() / 100), tank.getY() - (tank.getX_frame() / 100), tank.getX_frame() * 2 / 100, tank.getX_frame() * 2 / 100);
                graphics.fill3DRect(tank.getX() + (tank.getX_frame() / 100), tank.getY() - (tank.getX_frame() * 2 / 1000), tank.getX_frame() * 4 / 100, tank.getX_frame() * 4 / 1000, false);
                break;
            case 3:
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 2 / 100), tank.getY() - (tank.getX_frame() * 3 / 100), tank.getX_frame() / 100, tank.getX_frame() * 6 / 100, false);
                graphics.fill3DRect(tank.getX() + (tank.getX_frame() / 100), tank.getY() - (tank.getX_frame() * 3 / 100), tank.getX_frame() / 100, tank.getX_frame() * 6 / 100, false);
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() / 100), tank.getY() - (tank.getX_frame() * 2 / 100), tank.getX_frame() * 2 / 100, tank.getX_frame() * 4 / 100, false);
                graphics.fillOval(tank.getX() - (tank.getX_frame() / 100), tank.getY() - (tank.getX_frame() / 100), tank.getX_frame() * 2 / 100, tank.getX_frame() * 2 / 100);
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 2 / 1000), tank.getY() + (tank.getX_frame() / 100), tank.getX_frame() * 4 / 1000, tank.getX_frame() * 4 / 100, false);
                break;
            case 4:
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 3 / 100), tank.getY() - (tank.getX_frame() * 2 / 100), tank.getX_frame() * 6 / 100, tank.getX_frame() / 100, false);
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 3 / 100), tank.getY() + (tank.getX_frame() / 100), tank.getX_frame() * 6 / 100, tank.getX_frame() / 100, false);
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 2 / 100), tank.getY() - (tank.getX_frame() / 100), tank.getX_frame() * 4 / 100, tank.getX_frame() * 2 / 100, false);
                graphics.fillOval(tank.getX() - (tank.getX_frame() / 100), tank.getY() - (tank.getX_frame() / 100), tank.getX_frame() * 2 / 100, tank.getX_frame() * 2 / 100);
                graphics.fill3DRect(tank.getX() - (tank.getX_frame() * 5 / 100), tank.getY() - (tank.getX_frame() * 2 / 1000), tank.getX_frame() * 4 / 100, tank.getX_frame() * 4 / 1000, false);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //通过wasd键的点击来决定坦克方向以及行进

        if (e.getKeyCode() == KeyEvent.VK_W) {
            myTank.setDirect(1);
            myTank.moveUp();

        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            myTank.setDirect(2);
            myTank.moveRight();

        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            myTank.setDirect(3);
            myTank.moveDown();

        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            myTank.setDirect(4);
            myTank.moveLeft();

        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
            myTank.fire();
        }

        //重新绘制
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void hitTank(Bullet bullet, Tank tank) {
        if (bullet.isLive() != true || tank.isLive() != true) {
            return;
        }

        //System.out.println("Hit Tank 1");
        if (bullet.getSubordinate() != tank.getType()) {
            //System.out.println("Hit Tank 2");

            switch (tank.getDirect()) {
                case 1:
                case 3:
                    //子弹与坦克碰撞
                    System.out.println("case1 & case3");

                    if ((bullet.getX() - tank.getX()) <= 20 && (bullet.getX() - tank.getX()) >= -20
                            && (bullet.getY() - tank.getY()) <= 30 && (bullet.getY() - tank.getY()) >= -30) {
                        //碰撞后，子弹线程结束，坦克线程结束
                        //drawExplodeImage(tank,getGraphics());
                        System.out.println(tank.getClass().getSimpleName() + "was hit");
                        explodeVector.add(new Explode(tank.getX(), tank.getY()));
                        bullet.setLive(false);
                        tank.setLive(false);


                        //如果坦克是敌方坦克就在enemytanks中删除
                        if (tank.getType() == 1) {
                            enemyTanks.remove(tank);
                        }
                    }

                    break;
                case 2:
                case 4:
                    System.out.println("case2 & case4");

                    if ((bullet.getX() - tank.getX()) <= 30 && (bullet.getX() - tank.getX()) >= -30
                            && (bullet.getY() - tank.getY()) <= 20 && (bullet.getY() - tank.getY()) >= -20) {

                        System.out.println(tank.getClass().getSimpleName() + "was hit");

                        explodeVector.add(new Explode(tank.getX(), tank.getY()));
                        bullet.setLive(false);
                        tank.setLive(false);

                        if (tank.getType() == 1) {
                            enemyTanks.remove(tank);
                        }
                    }

                    break;
            }
        }
    }

    public void drawExplodeImage(Tank tank, Graphics graphics) {
        //System.out.println("Draw explode image!");
        graphics.drawImage(image_explode_1, tank.getX() - 30, tank.getY() - 30, this);
    }

    public void tankHit(Tank tank1, Tank tank2) {
        if (tank1.getDirect() == tank2.getDirect()) {
            return;
        }
        switch (tank1.getDirect()) {
            case 1:
                switch (tank2.getDirect()) {
                    case 2:
                    case 3:
                        if (tank1.getY() - tank2.getY() <= -50 && tank1.getX() - tank2.getX() <= 20 && tank1.getX() - tank2.getX() >= -20) {
                            tank1.setMoveable(false);
                        } else {
                            tank1.setMoveable(true);
                        }
                        break;
                    case 4:
                        if (tank1.getY() - tank2.getY() <= -60 && tank1.getX() - tank2.getX() <= 20 && tank1.getX() - tank2.getX() >= -20) {
                            tank1.setMoveable(false);
                            tank2.setMoveable(false);
                        }
                        break;
                }
                break;

            case 2:
                switch (tank2.getDirect()) {
                    case 1:
                    case 3:
                        if (tank1.getX() - tank2.getX() <= -50 && tank1.getY() - tank2.getY() <= 20 && tank1.getY() - tank2.getY() >= -20) {
                            tank1.setMoveable(false);
                        } else {
                            tank1.setMoveable(true);
                        }
                        break;
                    case 4:
                        if (tank1.getX() - tank2.getX() <= 60 && tank1.getY() - tank2.getY() <= 20 && tank1.getY() - tank2.getY() >= -20) {
                            tank1.setMoveable(false);
                            tank2.setMoveable(false);
                        }
                        break;
                }
                break;
            case 3:
                switch (tank2.getDirect()) {
                    case 2:
                    case 4:
                        if (tank1.getY() - tank2.getY() <= 50 && tank1.getX() - tank2.getX() <= 20 && tank1.getX() - tank2.getX() >= -20) {
                            tank1.setMoveable(false);
                        } else {
                            tank1.setMoveable(true);
                        }
                        break;
                    case 1:
                        if (tank1.getY() - tank2.getY() <= 60 && tank1.getX() - tank2.getX() <= 20 && tank1.getX() - tank2.getX() >= -20)  {
                            tank1.setMoveable(false);
                            tank2.setMoveable(false);
                        }
                        break;
                }
                break;

            case 4:
                switch (tank2.getDirect()) {
                    case 2:
                    case 3:
                        if (tank1.getY() - tank2.getY() <= -50 && tank1.getY() - tank2.getY() <= 20 && tank1.getY() - tank2.getY() >= -20) {
                            tank1.setMoveable(false);
                        } else {
                            tank1.setMoveable(true);
                        }
                        break;
                    case 4:
                        if (tank1.getY() - tank2.getY() <= -60 && tank1.getY() - tank2.getY() <= 20 && tank1.getY() - tank2.getY() >= -20) {
                            tank1.setMoveable(false);
                            tank2.setMoveable(false);
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {

                for (int j = 0; j < myTank.getBulletVector().size(); j++) {
                    //获取对象并判断碰撞

                    Bullet bullet_temp_mine = myTank.getBulletVector().get(j);

                    if (bullet_temp_mine != null && bullet_temp_mine.isLive()) {
                        for (int i = 0; i < enemyTanks.size(); i++) {

                            if (enemyTanks.get(i) != null) {
                                Tank tank = enemyTanks.get(i);
                                hitTank(bullet_temp_mine, tank);

                            }
                        }
                    }
                }

                for(int i =0; i < enemyTanks.size() - 1; i++){
                    for(int j = i+1; j < enemyTanks.size(); j++){
                        tankHit(enemyTanks.get(i),enemyTanks.get(j));
                    }
                }

                for (int i = 0; i < enemyTanks.size(); i++) {
                    for (int j = 0; j < enemyTanks.get(i).getBulletVector().size(); j++) {
                        Bullet bullet_temp_emeny = enemyTanks.get(i).getBulletVector().get(j);
                        hitTank(bullet_temp_emeny, myTank);
                    }
                }
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }

    }
}
