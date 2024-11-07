package game3;

import utilities.Vector2D;

import java.awt.*;

import static game3.Constants.*;

public abstract class GameObject {
    public Vector2D position;
    public Vector2D velocity;
    public double radius;
    public boolean dead;


    public GameObject(Vector2D position, Vector2D velocity, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
    }

    public void update() {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void hit() {
        dead = true;
    }

    public boolean overlap(GameObject other) {
        return this.position.distWithWrap(other.position, FRAME_WIDTH, FRAME_HEIGHT) < this.radius + other.radius;
    }

    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass()  && this.overlap(other)) {
            if((this instanceof Asteroid && other instanceof Planet)||(this instanceof Asteroid && other instanceof EnemyShip)||(this instanceof Planet && other instanceof EnemyShip)){}
            else{
            this.hit();
            other.hit();}
            if ((this instanceof Asteroid && other instanceof Bullet)||(this instanceof EnemyShip && other instanceof Bullet)) {
                    Bullet b = (Bullet) other;
                    if (b.firedByShip) Game.incScore(100);
            }
        }
    }

    public abstract void draw(Graphics2D g);

    public String toString() {
        return position.x +"," + position.y;
    }
}
