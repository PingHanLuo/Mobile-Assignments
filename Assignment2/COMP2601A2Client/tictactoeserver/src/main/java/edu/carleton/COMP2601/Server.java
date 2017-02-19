package edu.carleton.COMP2601;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.carleton.COMP2601.communication.Event;
import edu.carleton.COMP2601.communication.EventHandler;
import edu.carleton.COMP2601.communication.EventSourceImpl;
import edu.carleton.COMP2601.communication.Fields;
import edu.carleton.COMP2601.communication.Reactor;
import edu.carleton.COMP2601.communication.ThreadWithReactor;

public class Server {
    public static final String ipAddr = "192.168.0.17";
    public static final int PORT = 5001;
    private Reactor reactor;
    private EventSourceImpl es;
    private ThreadWithReactor twr;
    private ConcurrentHashMap<String, ThreadWithReactor> users;

    public Server(){
        users = new ConcurrentHashMap<>();
        reactor = new Reactor();
        reactor.register("connect", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                try {
                    Event response = new Event("connect", es);
                    //get hashmap of users
                    ArrayList<String> otherUsers = new ArrayList<String>();
                    for(Object user : ((Map)users).keySet()){
                        otherUsers.add((String)user);
                    }
                    users.put((String) event.get(Fields.ID), twr);
                    event.put("users",otherUsers);
                    es.putEvent(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args){
        Server ns = new Server();
        ns.run();
    }
    public void run(){
        ServerSocket listener;
        try{
            listener = new ServerSocket(PORT);
            while(true){
                Socket s = listener.accept();
                InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream();
                es = new EventSourceImpl(is,os);
                twr = new ThreadWithReactor(es, reactor);
                twr.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
