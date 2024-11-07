package game3;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.List;

import static game3.Constants.FRAME_WIDTH;
import static game3.Constants.FRAME_HEIGHT;

public class View extends JComponent {
    public static final Color BG_COLOR = Color.BLACK;
    private Game game;
    public HighScore highScore = new HighScore();
    Image im = Constants.MILKYWAY1;
    AffineTransform bgTransf;
    public View(Game game){
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 :
                Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 :
                Constants.FRAME_HEIGHT/imHeight);
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
    }
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.drawImage(im, bgTransf,null);
        synchronized (Game.class) {
            for (GameObject object : game.objects)
                object.draw(g);
        }
        g.setColor(Color.YELLOW);g.setFont(new Font("dialog", Font.BOLD, 20));g.drawString("Level: "+Game.getLevel(), 20, FRAME_HEIGHT-20);
        g.drawString("Score: "+Game.getScore(), FRAME_WIDTH/3+20, FRAME_HEIGHT-20);
        g.drawString("Lives: "+Game.getLives(), 2*FRAME_WIDTH/3+20, FRAME_HEIGHT-20);
        if (Game.getLives()==0){
            g.drawString("GAME OVER Score "+Game.getScore(), FRAME_WIDTH/2-100, FRAME_HEIGHT/2-20);
            try {
                highScore.writeScore(Game.getScore());//saves score
                List<Integer> scores = highScore.readFile();
                for(int i = 0; i < 5; i++){//displays top 5 high scores
                    g.drawString(String.valueOf(scores.get(i)),  FRAME_WIDTH/2+90, FRAME_HEIGHT/2+(20*i));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}
