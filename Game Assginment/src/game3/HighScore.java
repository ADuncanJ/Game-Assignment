package game3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScore {
    List<Integer> Highscores = new ArrayList<>();
    public List readFile()throws IOException{//reads scores in from file
        BufferedReader br = new BufferedReader(new FileReader("Highscores.txt"));
        String line = br.readLine();
        while(line!=null){
            Highscores.add(Integer.parseInt(line));
            line = br.readLine();
        }
        br.close();
        Collections.sort(Highscores);
        Collections.reverse(Highscores);//sorts in descending order
        return Highscores;
    }
    public void writeScore (int score)throws IOException {//writes score to file
        FileWriter f = new FileWriter("Highscores.txt", true);
        BufferedWriter b = new BufferedWriter(f);
        b.write(Integer.toString(score));
        b.newLine();
        b.close();
    }
}
