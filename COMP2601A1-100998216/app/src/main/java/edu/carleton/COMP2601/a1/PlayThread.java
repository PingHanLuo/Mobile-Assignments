package edu.carleton.COMP2601.a1;

import android.os.AsyncTask;
import android.widget.ImageButton;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import edu.carleton.COMP2601.R;

/**
 * Created by Luo on 2017-01-19.
 */

public class PlayThread extends Thread {
    MainActivity main;
    Game game;
    int count;
    boolean isRunning;
    public PlayThread(MainActivity main, Game game){
        this.main = main;
        this.game = game;
        count = 0;
        isRunning=false;
    }
    //thread that wait for 2 seconds then places
    public void run(){
        isRunning=true;
        Random rand = new Random();
        int i = rand.nextInt(9);
        try {
            Thread.sleep(2000);
            while (isRunning) {
                while(game.numSet.contains(i)){
                    i = rand.nextInt(9);
                }
                game.place(i, false);
                game.numSet.add(i);
                main.updateTile(i,game.getRecentSymbol());
                main.update();
                i = rand.nextInt(9);
                Thread.sleep(2000);
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        //update UI
        //main.update();
    }

}
