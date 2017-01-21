package edu.carleton.COMP2601.a1;

import android.os.AsyncTask;

/**
 * Created by Luo on 2017-01-19.
 */

public class PlayThread extends Thread {
    MainActivity main;
    public PlayThread(MainActivity main){
        this.main = main;
    }
    //thread that wait for 2 seconds then places
    public void run(){
        //update UI
        main.update();

    }
}
