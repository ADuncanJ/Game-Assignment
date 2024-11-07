package game3;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game3.Constants.*;

public class Ship extends GameObject {
    public static final int RADIUS = 8;

    public static final double STEER_RATE = 2 * Math.PI;

    public static final double MAG_ACC = 200;

    public static final double DRAG = 0.01;

    public static final double DRAWING_SCALE = 1.5;

    public static final int MUZZLE_VELOCITY = 100;

    public static final Color COLOR = Color.gray;

    public static final Color SHIELD_COLOUR1 = Color.blue;
    public static final Color SHIELD_COLOUR2 = Color.orange;
    public static final Color SHIELD_COLOUR3 = Color.red;

    public static final int SHIELD = 9;
    public static final int SHEILD_RECHARGE_TIME = 90;
    public static int shield_health = SHIELD;
    public static int shield_recharge = SHEILD_RECHARGE_TIME;

    public Vector2D direction;

    public boolean thrusting;


    public Bullet bullet;

    public static final int MAX_BULLETS_PER_SECOND = 15;

    public static final double BULLET_INTERVAL = 1000.0 / MAX_BULLETS_PER_SECOND;  // in milliseconds

    private double last_bullet_time = System.currentTimeMillis();

    public int XP[] = { -6, 0, 6, 0 }, YP[] = { 8, 4, 8, -8 };
    public int XPTHRUST[] = { -5, 0, 5, 0 }, YPTHRUST[] = { 7, 3, 7, -7 };

    private Controller ctrl;

    public Ship(Controller ctrl) {
        super(new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2), new Vector2D(0, -1), 10);
        this.ctrl = ctrl;
        direction = new Vector2D(0,-1);
        thrusting = false;
        bullet = null;
    }

    private void mkBullet(){
        Vector2D bulletPos = new Vector2D(position);
        Vector2D bulletVel = new Vector2D(velocity);
        bulletVel.addScaled(direction, MUZZLE_VELOCITY);
        bullet = new Bullet(bulletPos, bulletVel, true);
        bullet.position.addScaled(direction, (radius+bullet.radius)*2);
    }

    @Override
    public void update() {
        Action action = ctrl.action();
        if (action.shoot) {
            double time = System.currentTimeMillis();
            if (time - last_bullet_time >= BULLET_INTERVAL) {
                mkBullet();
                last_bullet_time = time;
                action.shoot = false;
                SoundManager.fire();
            }
        }
        thrusting = action.thrust != 0;
        direction.rotate(action.turn * STEER_RATE * DT);
        velocity = new Vector2D(direction).mult(velocity.mag());
        velocity.addScaled(direction, MAG_ACC * DT * action.thrust);
        velocity.addScaled(velocity, -DRAG);
        shield_recharge -=DT;
        if (thrusting) SoundManager.startThrust();
        if(shield_recharge <= 0){//shield recharge
            if(shield_health < 9) shield_health += 3;
            shield_recharge = SHEILD_RECHARGE_TIME;
        }
        else SoundManager.stopThrust();
        super.update();
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        if(shield_health >= 9)g.setColor(SHIELD_COLOUR1);//ship changes colour based on shield strength
        else if(shield_health >= 6)g.setColor(SHIELD_COLOUR2);
        else if(shield_health >= 3)g.setColor(SHIELD_COLOUR3);
        else g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        if (thrusting) {
            g.setColor(Color.red);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);
    }

    @Override
    public void hit() {
        if(shield_health > 0){//added check for if there is remaining shield
            shield_health--;
            shield_recharge = SHEILD_RECHARGE_TIME;
        }else{
        super.hit();
        Game.loseLife();
        shield_health = 9;
        shield_recharge = SHEILD_RECHARGE_TIME;
        SoundManager.play(SoundManager.bangLarge);
        }
    }

    public String toString() {
        return "Ship: " + super.toString();
    }
}
