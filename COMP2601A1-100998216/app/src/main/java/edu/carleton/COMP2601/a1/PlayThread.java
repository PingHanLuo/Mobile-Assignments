package edu.carleton.COMP2601.a1;

import android.os.AsyncTask;
import android.widget.ImageButton;

import edu.carleton.COMP2601.R;

/**
 * Created by Luo on 2017-01-19.
 */

public class PlayThread extends Thread {
    MainActivity main;
    int count;
    public PlayThread(MainActivity main){
        this.main = main;
        count = 0;
    }
    //thread that wait for 2 seconds then places
    public void run(){
        try{
            while(count < 9) {
                //for (int i = 0; i < main.tiles.length; i++) {
                    if (count % 2 == 0) {
                        main.xClick(count);
                    } else {
                        main.yClick(count);
                    }
                    count++;
                    Thread.sleep(100);
                //}
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        //update UI
        //main.update();
    }

}
