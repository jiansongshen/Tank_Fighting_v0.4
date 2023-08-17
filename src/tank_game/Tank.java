package tank_game;

import java.util.Vector;

public class Tank {
    private int x;//横坐标
    private int y;//纵坐标
    private int direct = 1;//方向
    private int x_frame;//画布宽度
    private int y_frame;//画布高度
    private int speed = 2;//速度
    private boolean live = true;//存活
    protected Vector<Bullet> bulletVector = new Vector<>();//子弹
    private int type;//1为敌方坦克，0为我方坦克
    private boolean moveable = true;//是否可以移动

    public Tank(int x, int y, int x_frame, int y_frame, int type) {
        this.x = x;
        this.y = y;
        this.x_frame = x_frame;
        this.y_frame = y_frame;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Vector<Bullet> getBulletVector() {
        return bulletVector;
    }

    public void setBulletVector(Vector<Bullet> bulletVector) {
        this.bulletVector = bulletVector;
    }



    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y, int x_frame, int y_frame) {
        this.x = x;
        this.y = y;
        this.x_frame = x_frame;
        this.y_frame = y_frame;
    }

    public int getX_frame() {
        return x_frame;
    }

    public void setX_frame(int x_frame) {
        this.x_frame = x_frame;
    }

    public int getY_frame() {
        return y_frame;
    }

    public void setY_frame(int y_frame) {
        this.y_frame = y_frame;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void moveUp(){
        if(this.y <= this.x_frame * 3 / 100){
            return;
        }
        if (isMoveable()) {
            this.y -= speed;
        }
    }

    public void moveRight(){
        if(this.x >= this.x_frame- this.x_frame * 5 / 100){
            return;
        }
        if (isMoveable()) {
            this.x += speed;
        }
    }

    public void moveDown(){
        if(this.y >= this.y_frame - this.x_frame * 7 / 100){
            return;
        }
        if (isMoveable()) {
            this.y += speed;
        }
    }

    public void moveLeft(){
        if(this.x <= this.x_frame * 3 / 100){
            return;
        }
        if (isMoveable()) {
            this.x -= speed;
        }
    }

    public void fire(){}
}
