package edu.carleton.COMP2601.comp2601a2client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.carleton.COMP2601.comp2601a2client.common.messaging.Message;
import edu.carleton.COMP2601.comp2601a2client.communication.Event;
import edu.carleton.COMP2601.comp2601a2client.communication.EventHandler;
import edu.carleton.COMP2601.comp2601a2client.communication.Fields;
import edu.carleton.COMP2601.comp2601a2client.communication.Reactor;

public class MainActivity extends AppCompatActivity {

    public static final String ipAddr = "192.168.0.17";
    String ID;
    Reactor reactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reactor = new Reactor();
        reactor.register("connect", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                ID = (String)event.get(Fields.ID);
                //fill players
            }
        });
        reactor.register("new user", new EventHandler() {
            @Override
            public void handleEvent(Event event) {

            }
        });
        connectToServer();
    }

    protected void connectToServer(){
        //Send CONNECT_REQUEST
        Message connectMessage = new Message();
        connectMessage.header.type = "connect";

    }
}
