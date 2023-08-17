package tank_game;

public class MyTank extends Tank {

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    private Bullet bullet;

    public MyTank(int x, int y) {
        super(x, y);
    }

    public MyTank(int x, int y, int x_frame, int y_frame, int type) {
        super(x, y, x_frame, y_frame, type);
    }

    public void fire() {
        //新建子弹对象
        if (bulletVector.size() <= 5) {
            Bullet bullet1 = new Bullet(this.getX(), this.getY(),
                    this.getSpeed() + 20, this.getDirect(), 0);
            bullet1.start();
            bulletVector.add(bullet1);
        }
    }
}
