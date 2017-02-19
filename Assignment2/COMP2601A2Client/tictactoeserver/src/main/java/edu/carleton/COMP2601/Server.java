package edu.carleton.COMP2601;

import java.util.concurrent.ConcurrentHashMap;

import edu.carleton.COMP2601.communication.Event;
import edu.carleton.COMP2601.communication.EventHandler;
import edu.carleton.COMP2601.communication.EventSourceImpl;
import edu.carleton.COMP2601.communication.Reactor;
import edu.carleton.COMP2601.communication.ThreadWithReactor;

public class Server {
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

            }
        });
    }
    public static void main(String[] args){
        Server ns = new Server();
        ns.run();
    }
    public void run(){

    }
}
