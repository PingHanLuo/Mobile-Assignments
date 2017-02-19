package edu.carleton.COMP2601.comp2601a2client.communication;

import java.io.IOException;

public interface EventSource {
	public Event getEvent() throws IOException, ClassNotFoundException;
	public void putEvent(Event e) throws IOException, ClassNotFoundException;
}
