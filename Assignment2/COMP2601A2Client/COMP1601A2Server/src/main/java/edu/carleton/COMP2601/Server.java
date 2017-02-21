package edu.carleton.COMP2601;

import org.json.JSONArray;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import edu.carleton.COMP2601.communication.Event;
import edu.carleton.COMP2601.communication.EventHandler;
import edu.carleton.COMP2601.communication.EventSourceImpl;
import edu.carleton.COMP2601.communication.Fields;
import edu.carleton.COMP2601.communication.Reactor;
import edu.carleton.COMP2601.communication.ThreadWithReactor;

public class Server {
    public static final int PORT = 7001;
    private Reactor reactor;
    private EventSourceImpl es;
    private ThreadWithReactor twr;
    private ConcurrentHashMap<String, ThreadWithReactor> users;

    private ConcurrentHashMap<String,Game> gameTracker;

    public Server(){
        users = new ConcurrentHashMap<>();
        gameTracker = new ConcurrentHashMap<>();
        reactor = new Reactor();
        reactor.register("CONNECT_REQUEST", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                try {
                    Event response = new Event("CONNECTED_RESPONSE", es);
                    //get hashmap of users
                    ArrayList<String> allUsers = new ArrayList<String>();
                    JSONArray jsonUsers = new JSONArray();
                    for(Object user : ((Map)users).keySet()){
                        allUsers.add((String)user);
                        jsonUsers.put(user);
                    }
                    allUsers.add((String)event.get(Fields.ID));
                    jsonUsers.put(event.get(Fields.ID));
                    users.put((String) event.get(Fields.ID), twr);
                    es.putEvent(response);
                    System.out.println((String)event.get(Fields.ID));

                    //notify other users that a new user have been added
                    HashMap<String,Serializable> hm = new HashMap<String, Serializable>();
                    hm.put("users",jsonUsers.toString());
                    for (String username : allUsers){
                        Event newUserEvent = new Event("USERS_UPDATED",users.get(username).getEventSource());
                        newUserEvent.put(Fields.BODY,hm);
                        users.get(username).getEventSource().putEvent(newUserEvent);
                        System.out.println("sending to " + username + hm.get("users").toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        reactor.register("PLAY_GAME_REQUEST", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                try {
                    String player = (String) event.get(Fields.RET_ID);
                    Event request = new Event("PLAY_GAME_REQUEST", users.get(player).getEventSource());
                    request.put(Fields.ID, event.get(Fields.ID));
                    users.get(player).getEventSource().putEvent(request);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        reactor.register("PLAY_GAME_RESPONSE", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                try {
                    String player = (String)event.get(Fields.RET_ID);
                    if((boolean)event.get("status") == true){
                        //start game
                        Game game = new Game();
                        gameTracker.put((String)event.get(Fields.ID),game);
                        gameTracker.put(player,game);
                    }
                    System.out.println(player);
                    Event response = new Event("PLAY_GAME_RESPONSE",users.get(player).getEventSource());
                    response.put(Fields.ID,event.get(Fields.ID));
                    HashMap<String, Serializable> hm = new HashMap<String, Serializable>();
                    hm.put("status",event.get("status"));
                    response.put(Fields.BODY,hm);
                    users.get(player).getEventSource().putEvent(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        reactor.register("DISCONNECT_REQUEST", new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                try {
                    System.out.println("Request Recieved");
                    users.remove((String)event.get(Fields.ID)).quit();
                    ArrayList<String> allUsers = new ArrayList<String>();
                    JSONArray jsonUsers = new JSONArray();
                    for (Object user : ((Map) users).keySet()) {
                        allUsers.add((String) user);
                        jsonUsers.put(user);
                    }
                    HashMap<String,Serializable> hm = new HashMap<String, Serializable>();
                    hm.put("users",jsonUsers.toString());
                    for (String username : allUsers) {
                        Event newUserEvent = new Event("USERS_UPDATED", users.get(username).getEventSource());
                        newUserEvent.put(Fields.BODY,hm);
                        users.get(username).getEventSource().putEvent(newUserEvent);
                    }
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
