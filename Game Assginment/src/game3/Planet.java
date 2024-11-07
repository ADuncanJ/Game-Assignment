package game3;

import utilities.Vector2D;

import java.awt.*;

import static game3.Constants.FRAME_HEIGHT;
import static game3.Constants.FRAME_WIDTH;
//modified asteroid
public class Planet extends GameObject{
    public static final int RADIUS = 100;
    public static final double MAX_SPEED = 0;

    public boolean isLarge;
    Sprite sprite;
    Vector2D direction;
    public Planet(double x, double y, double vx, double vy, boolean isLarge) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), 100.0);
        this.isLarge = isLarge;
        direction = new Vector2D(0, 1);
        sprite = new Sprite(Sprite.ASTEROID1, position, direction, radius * 2, RADIUS * 2);

    }
    public void draw(Graphics2D g) {
        sprite.draw(g);
    }

    public void update(){
        super.update();
    }

    public void hit(){
        dead = false;
    }

    public static Planet makeRandomPlanet(){
        double x = Math.random() * FRAME_WIDTH;
        if(x < 100){x = 100;}//position constrained to keep planet from wrapping
        if(x > (FRAME_WIDTH-100)){x = FRAME_WIDTH -100;}
        double y = Math.random() + FRAME_HEIGHT;
        if(y < 100){y = 100;}
        if(y > (FRAME_HEIGHT-100)){y = FRAME_HEIGHT -100;}
        double vx = Math.random() * MAX_SPEED;
        if (Math.random()<0.5) vx *= -1;
        double vy = Math.random() * MAX_SPEED;
        if (Math.random()<0.5) vy *= -1;
        return new Planet(x,y,vx,vy,true);

    }
}
