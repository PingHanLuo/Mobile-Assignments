package edu.carleton.COMP2601.comp2601a2client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.carleton.COMP2601.comp2601a2client.communication.Event;
import edu.carleton.COMP2601.comp2601a2client.communication.EventHandler;
import edu.carleton.COMP2601.comp2601a2client.communication.Fields;
import edu.carleton.COMP2601.comp2601a2client.communication.Reactor;

public class MainActivity extends AppCompatActivity {

    Reactor reactor;
    ListView userlist;
    ArrayAdapter listAdapter;
    NetworkingService ns;
    public static MainActivity mainActivity;
    //
    protected ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NetworkingService.MyBinder b = (NetworkingService.MyBinder)service;
            ns = b.getService();
            connectToServer();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            ns = null;
        }
    };

    public static MainActivity getInstance(){return mainActivity;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userlist = (ListView)findViewById(R.id.lstUsers);
        listAdapter = new ArrayAdapter(getApplicationContext(),R.layout.activity_main,R.id.lstUsers,new ArrayList<String>());
        userlist.setAdapter(listAdapter);
        setup();
    }

    protected void setup(){
        Intent network = new Intent(getApplicationContext(),NetworkingService.class);
        startService(network);
        bindService(network,serviceConnection, Context.BIND_AUTO_CREATE);
        reactor = new Reactor();
        reactor.register("connect", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                //fill players
                listAdapter.addAll((ArrayList<String>)event.get("users"));
                Toast.makeText(getApplicationContext(),"recieved response",Toast.LENGTH_SHORT).show();
            }
        });
        reactor.register("new user", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                listAdapter.add(event.get("user"));
            }
        });
    }

    //send first connect message
    protected void connectToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ns.connect(Double.toString(Math.random()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
