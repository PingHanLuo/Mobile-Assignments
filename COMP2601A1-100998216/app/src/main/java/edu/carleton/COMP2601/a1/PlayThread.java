package edu.carleton.COMP2601.a1;

import android.os.AsyncTask;
import android.widget.ImageButton;

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
        int i;
        try {
            Thread.sleep(2000);
            while (isRunning) {
                for(i=0;i<9;i++){
                    if(game.place(i, false))
                        break;
                }
                main.updateTile(i,game.getRecentSymbol());
                main.update();
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
