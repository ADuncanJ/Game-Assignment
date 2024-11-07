package game3;

import utilities.SoundManager;
import utilities.Vector2D;
import java.util.ArrayList;

import static game3.Constants.FRAME_HEIGHT;
import static game3.Constants.FRAME_WIDTH;
import java.awt.Graphics2D;
import java.util.List;

public class Asteroid extends GameObject{
    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;

    public static double ROTATION_PER_FRAME = Math.PI/50;
    public boolean isLarge;
    public static List<Asteroid> spawnedAsteroids = new ArrayList<Asteroid>();
    Sprite sprite;
 Vector2D direction;
    public Asteroid(double x, double y, double vx, double vy, boolean isLarge) {
        super(new Vector2D(x,y), new Vector2D(vx, vy), 10.0);
        this.isLarge = isLarge;
        radius = isLarge ? RADIUS : 2*RADIUS/3.0;
        direction = new Vector2D(0, 1);
        sprite = new Sprite(Sprite.ASTEROID1, position, direction, radius*2, radius*2);

    }

    public void draw(Graphics2D g){
        sprite.draw(g);
    }

    public void update(){
        super.update();
        direction.rotate(ROTATION_PER_FRAME);
    }

    public static Asteroid makeRandomAsteroid(){
        double x = Math.random() * FRAME_WIDTH;
        double y = Math.random() + FRAME_HEIGHT;
        double vx = Math.random() * MAX_SPEED;
        if (Math.random()<0.5) vx *= -1;
        double vy = Math.random() * MAX_SPEED;
        if (Math.random()<0.5) vy *= -1;
        return new Asteroid(x,y,vx,vy,true);

    }

    private void spawn(){
        for (int i = 0; i<2; i++) {
            double vx = Math.random() * MAX_SPEED;
            if (Math.random() < 0.5) vx *= -1;
            double vy = Math.random() * MAX_SPEED;
            if (Math.random() < 0.5) vy *= -1;
            spawnedAsteroids.add( new Asteroid(position.x, position.y, vx, vy, false));
        }

    }

    @Override
    public void hit() {
        super.hit();

        if (isLarge)
            spawn();
        SoundManager.play(SoundManager.bangSmall);
    }

    public String toString() {
        return "Asteroid: " + super.toString();
    }
}
