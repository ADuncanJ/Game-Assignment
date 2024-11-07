package game3;

import utilities.Vector2D;

import java.awt.*;

import static game3.Asteroid.spawnedAsteroids;
import static game3.Constants.FRAME_HEIGHT;
import static game3.Constants.FRAME_WIDTH;
import static game3.Constants.DT;
//based on the asteroid class
public class EnemyShip extends GameObject{
    public static final double MAX_SPEED = 100;
    public static final int THRUST_PERIOD = 3;
    private double thrust_time;
    Sprite sprite;
    Vector2D direction;
    public EnemyShip(double x, double y, double vx, double vy) {
        super(new Vector2D(x,y), new Vector2D(vx, vy), 10.0);
        direction = new Vector2D(0, 1);
        sprite = new Sprite(Sprite.ASTEROID1, position, direction, radius*3, radius*2);

    }

    public void draw(Graphics2D g){
        sprite.draw(g);
        g.setColor(Color.RED); //red to denote enemy
        g.fillOval((int) (position.x-radius), (int) (position.y-radius), (int)(3*radius), (int)(2*radius));// oval to make it saucer-like
    }

    public void update(){
        super.update();
        thrust_time -= DT;
        if(thrust_time <=0){//after thrusting for the prerequisite time it fires and changes direction
            fire();
            double vx = Math.random() * MAX_SPEED;
            if (Math.random()<0.5) vx *= -1;
            double vy = Math.random() * MAX_SPEED;
            if (Math.random()<0.5) vy *= -1;
            this.velocity = new Vector2D(vx,vy);
            thrust_time = THRUST_PERIOD;
        }
    }


    public void fire(){
        double vx = 0;
        double vy = 0;
        for (int i = 0; i<4; i++) {// fires four asteroids to up,down,left and right
            if (i == 0){
                vx = MAX_SPEED;
                vy = 0;
            } else if (i == 1){
                vx = 0;
                vy = MAX_SPEED;
            } else if (i == 2){
                vx = -MAX_SPEED;
                vy = 0;
            }else if (i == 3){
                vx = 0;
                vy = -MAX_SPEED;
            }

            spawnedAsteroids.add( new Asteroid(position.x, position.y, vx, vy, false));
        }
    }

    public static EnemyShip makeEnemyShip(){
        double x = Math.random() * FRAME_WIDTH;
        double y = Math.random() + FRAME_HEIGHT;
        double vx = Math.random() * MAX_SPEED;
        if (Math.random()<0.5) vx *= -1;
        double vy = Math.random() * MAX_SPEED;
        if (Math.random()<0.5) vy *= -1;
        return new EnemyShip(x,y,vx,vy);

    }
}
