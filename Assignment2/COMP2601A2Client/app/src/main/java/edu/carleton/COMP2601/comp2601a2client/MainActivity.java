package edu.carleton.COMP2601.comp2601a2client;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import edu.carleton.COMP2601.comp2601a2client.communication.Event;
import edu.carleton.COMP2601.comp2601a2client.communication.EventHandler;
import edu.carleton.COMP2601.comp2601a2client.communication.EventSourceImpl;
import edu.carleton.COMP2601.comp2601a2client.communication.Fields;
import edu.carleton.COMP2601.comp2601a2client.communication.Reactor;
import edu.carleton.COMP2601.comp2601a2client.communication.ThreadWithReactor;

public class MainActivity extends AppCompatActivity {

    public final String USERID = "Bob";

    Reactor reactor;
    ListView userlist;
    ArrayAdapter listAdapter;
    NetworkingService ns;
    ArrayList<String> users;
    EventSourceImpl es;
    public static MainActivity mainActivity;
    //service connection manager
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
        users = new ArrayList<String>();
        listAdapter = new ArrayAdapter(getApplicationContext(),R.layout.activity_main,R.id.lstUsers,users);
        userlist.setAdapter(listAdapter);
        setup();
    }

    protected void setup(){
        Intent network = new Intent(getApplicationContext(),NetworkingService.class);
        startService(network);
        bindService(network,serviceConnection, Context.BIND_AUTO_CREATE);
        reactor = new Reactor();
        reactor.register("CONNECT_REQUEST", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                Toast.makeText(getApplicationContext(),"Connected!",Toast.LENGTH_SHORT).show();
            }
        });
        reactor.register("USERS_UPDATED", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                //fill players
                users.addAll((ArrayList<String>)event.get("users"));
            }
        });
        reactor.register("PLAY_GAME_REQUEST", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                //create response event
                AlertDialog.Builder confirmDialog = new AlertDialog.Builder(getApplicationContext());
                confirmDialog.setMessage((String)event.get(Fields.ID) + " wants to play");
                confirmDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Event response = new Event("PLAY_GAME_RESPONSE",es);
                            response.put(Fields.ID,USERID);
                            response.put("status",true);
                            es.putEvent(response);
                            //start game
                            Intent gameIntent = new Intent(getApplicationContext(),GameActivity.class);
                            startActivity(gameIntent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                confirmDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Event response = new Event("PLAY_GAME_RESPONSE",es);
                            response.put(Fields.ID,USERID);
                            response.put("status",false);
                            es.putEvent(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    //send first connect message
    protected void connectToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ns.connect(USERID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
