package edu.carleton.COMP2601.comp2601a2client;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.Socket;

import edu.carleton.COMP2601.comp2601a2client.communication.Event;
import edu.carleton.COMP2601.comp2601a2client.communication.EventSourceImpl;
import edu.carleton.COMP2601.comp2601a2client.communication.Fields;
import edu.carleton.COMP2601.comp2601a2client.communication.ThreadWithReactor;

/**
 * Created by Luo on 2017-02-20.
 */

public class NetworkingService extends Service {
    int port = 5001;
    String addr = "192.168.0.17";
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
    public void connect(String userid) throws IOException, ClassNotFoundException{
        s = new Socket(addr,port);
        es = new EventSourceImpl(s.getOutputStream(),s.getInputStream());
        twr = new ThreadWithReactor(es,MainActivity.getInstance().reactor);
        //Send CONNECT_REQUEST
        Event connectEvent = new Event("connect",es);
        connectEvent.put(Fields.ID,userid);
        es.putEvent(connectEvent);
        twr.start();
    }

}
