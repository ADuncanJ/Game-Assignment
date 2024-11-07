package game3;

import utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;

import static game3.Constants.DELAY;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public List<GameObject> objects;
    Ship ship;
    Keys ctrl;
    private static int score = 0;
    private static int lives = 5;
    private static int level = 1;
    public static boolean gameOver = false;

    public Game() {
        objects = new ArrayList<GameObject>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());

        }
        objects.add(Planet.makeRandomPlanet());
        objects.add(EnemyShip.makeEnemyShip());
        ctrl = new Keys();
        ship = new Ship(ctrl);
        objects.add(ship);
    }

    public void newLevel() {
        level++;
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {

        }
        synchronized(Game.class) {
            objects.clear();
            for (int i = 0; i < N_INITIAL_ASTEROIDS + 2 * (level-1); i++) {
                objects.add(Asteroid.makeRandomAsteroid());

            }
            objects.add(Planet.makeRandomPlanet());
            objects.add(EnemyShip.makeEnemyShip());
            ship = new Ship(ctrl);
            objects.add(ship);
        }

    }

    public void newLife() {
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {

        }
        synchronized(Game.class) {
            objects.clear();
            for (int i = 0; i < N_INITIAL_ASTEROIDS + 2 * (level-1); i++) {
                objects.add(Asteroid.makeRandomAsteroid());

            }
            objects.add(Planet.makeRandomPlanet());
            objects.add(EnemyShip.makeEnemyShip());
            ship = new Ship(ctrl);
            objects.add(ship);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "BasicGame").addKeyListener(game.ctrl);
        while (!gameOver) {
            game.update();
            view.repaint();
            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
            }


        }
    }

    public void update() {

        for (int i = 0; i < objects.size(); i++) {
            GameObject o1 = objects.get(i);
            for (int j = i + 1; j < objects.size(); j++) {
                objects.get(i).collisionHandling(objects.get(j));
            }
        }
        List<GameObject> alive = new ArrayList<>();
        boolean noAsteroids = true;
        boolean noShip = true;
        for (GameObject o : objects) {
            o.update();
            if (o instanceof Asteroid) {
                noAsteroids = false;
                Asteroid a = (Asteroid) o;
                if (!a.spawnedAsteroids.isEmpty()) {
                    alive.addAll(a.spawnedAsteroids);
                    a.spawnedAsteroids.clear();
                }
            }
            else if (o instanceof Ship) noShip = false;
            if (!o.dead) alive.add(o);
            if (ship.bullet != null) {
                alive.add(ship.bullet);
                ship.bullet = null;
            }
        }
        synchronized (Game.class) {
            objects.clear();
            objects.addAll(alive);
        }
        if (noAsteroids) {
            newLevel();
        }
        else if (noShip) {
            newLife();
        }
    }

    public static void incScore(int inc) {
        int oldScore = score;
        score += inc;
        System.out.println("Score " + score);
        if (score / 5000 > oldScore / 5000) {
            System.out.println("Adding life");
            lives++;
        }
    }

    public static void loseLife()  {
        lives--;
        if (lives==0)
            gameOver = true;
    }

    public static int getScore() {
        return score;
    }

    public static int getLevel() {
        return level;
    }

    public static int getLives() {
        return lives;
    }


}

