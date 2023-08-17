package tank_game;

public class Bullet extends Thread {

    private int x;//x坐标
    private int y;//y坐标
    private int subordinate;//所属
    private int speed = 10;//速度
    private int direct = 1;//方向
    public int getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(int subordinate) {
        this.subordinate = subordinate;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    private boolean live = true;

    public Bullet(int x, int y, int speed, int direct,int subordinate) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direct = direct;
        this.subordinate = subordinate;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Bullet(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public void run() {
        while (x < 1000 && x > 0 && y < 750 && y > 0 && isLive() == true) {
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direct) {
                case 1://上
                    this.moveUp();
                    break;
                case 2://右
                    this.moveRight();
                    break;
                case 3://下
                    this.moveDown();
                    break;
                case 4://左
                    this.moveLeft();
                    break;

            }
            //System.out.println("当前位置 ： x = " + x + " y = " + y);
        }
        live = false;
    }

    public void moveUp() {
        this.y -= speed;
    }

    public void moveRight() {
        this.x += speed;
    }

    public void moveDown() {
        this.y += speed;
    }

    public void moveLeft() {
        this.x -= speed;
    }
}
