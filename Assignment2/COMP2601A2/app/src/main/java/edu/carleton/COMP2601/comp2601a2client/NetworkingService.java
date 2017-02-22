package edu.carleton.COMP2601.comp2601a2client;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;

import edu.carleton.COMP2601.common.messaging.Message;
import edu.carleton.COMP2601.communication.Event;
import edu.carleton.COMP2601.communication.EventSourceImpl;
import edu.carleton.COMP2601.communication.Fields;
import edu.carleton.COMP2601.communication.ThreadWithReactor;

/**
 * Created by Luo on 2017-02-20.
 */

public class NetworkingService extends Service {
    int port = 7001;
    //home ip
    String addr = "192.168.0.17";
    //carleton ip
    //String addr = "172.17.42.172";
    //standard
//    String addr = "10.0.0.1";
    Socket s;
    EventSourceImpl es;
    ThreadWithReactor twr;
    private IBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        NetworkingService getService() {
            return NetworkingService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public void connect(String userid) throws IOException{
        s = new Socket(addr,port);
        es = new EventSourceImpl(s.getOutputStream(),s.getInputStream());
        twr = new ThreadWithReactor(es,MainActivity.getInstance().reactor);
        //Send CONNECT_REQUEST
        Event connectEvent = new Event("CONNECT_REQUEST",es);
        connectEvent.put(Fields.ID,userid);
        es.putEvent(connectEvent);
        twr.start();
    }

    public void request(String name) throws IOException{
        Event gameRequest = new Event("PLAY_GAME_REQUEST",es);
        gameRequest.put(Fields.ID,MainActivity.getInstance().userid);
        gameRequest.put(Fields.RET_ID,name);
        es.putEvent(gameRequest);
    }

    public void disconnect() throws IOException {
        Event disconnectRequest = new Event("DISCONNECT_REQUEST",es);
        disconnectRequest.put(Fields.ID,MainActivity.getInstance().userid);
        es.putEvent(disconnectRequest);
    }

    public void startGame() throws IOException{
        Event gameOn = new Event("GAME_ON",es);
        gameOn.put(Fields.ID,MainActivity.getInstance().userid);
        gameOn.put(Fields.RET_ID,MainActivity.getInstance().opponent);
        es.putEvent(gameOn);
    }

    public void place(int i) throws IOException{
        Event placeRequest = new Event("MOVE_MESSAGE",es);
        placeRequest.put(Fields.ID,MainActivity.getInstance().userid);
        placeRequest.put(Fields.RET_ID,MainActivity.getInstance().opponent);
        HashMap<String, Serializable> hm = new HashMap<>();
        hm.put("move",i);
        placeRequest.put(Fields.BODY,hm);
        es.putEvent(placeRequest);
    }
    public void endGame() throws IOException{
        Event endRequest = new Event("GAME_OVER",es);
        endRequest.put(Fields.ID,MainActivity.getInstance().userid);
        endRequest.put(Fields.RET_ID,MainActivity.getInstance().opponent);
        es.putEvent(endRequest);
    }
}
