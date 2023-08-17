package tank_game;

public class EnemyTank extends Tank implements Runnable {


    public EnemyTank(int x, int y, int x_frame, int y_frame) {
        super(x, y, x_frame, y_frame);
    }

    public EnemyTank(int x, int y, int x_frame, int y_frame, int type) {
        super(x, y, x_frame, y_frame, type);
    }

    @Override
    public void run() {
        while (isLive()) {
            //给敌方坦克子弹
            if(getBulletVector().size() <= 5){
                Bullet bullet = new Bullet(getX(), getY(),
                        getSpeed() + 20, getDirect(), 1);
                bullet.start();
                getBulletVector().add(bullet);
            }

            //给予随机方向
            setDirect((int)(Math.random() * 4) + 1);

            switch (getDirect()) {
                case 1:
                    for (int i = 0; i < 20; i++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        moveUp();
                    }
                    break;
                case 2:
                    for (int j = 0; j < 20;j++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        moveRight();
                    }
                    break;
                case 3:
                    for (int k = 0; k < 20; k++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        moveDown();
                    }
                    break;
                case 4:
                    for (int l = 0; l < 20; l++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        moveLeft();
                    }
                    break;
            }


        }

    }
}
