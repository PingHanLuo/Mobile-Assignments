package edu.carleton.COMP2601.comp2601a2client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.carleton.COMP2601.comp2601a2client.common.messaging.Message;
import edu.carleton.COMP2601.comp2601a2client.communication.Event;
import edu.carleton.COMP2601.comp2601a2client.communication.EventHandler;
import edu.carleton.COMP2601.comp2601a2client.communication.Fields;
import edu.carleton.COMP2601.comp2601a2client.communication.Reactor;

public class MainActivity extends AppCompatActivity {

    public static final String ipAddr = "192.168.0.17";
    String ID;
    Reactor reactor;
    ListView userlist;
    ArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userlist = (ListView)findViewById(R.id.lstUsers);
        listAdapter = new ArrayAdapter(getApplicationContext(),R.layout.activity_main,R.id.lstUsers,new ArrayList<String>());
        userlist.setAdapter(listAdapter);
        reactor = new Reactor();
        reactor.register("connect", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                ID = (String)event.get(Fields.ID);
                //fill players
                listAdapter.addAll((ArrayList<String>)event.get("users"));
            }
        });
        reactor.register("new user", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                listAdapter.add(event.get("user"));
            }
        });
        connectToServer();
    }

    protected void connectToServer(){
        //Send CONNECT_REQUEST
        Message connectMessage = new Message();
        connectMessage.header.type = "connect";
        connectMessage.header.id = "bob";
    }
}
