package websocket;

import org.eclipse.jetty.websocket.api.Session;
import java.util.*;

public class WebSocketSessions {
    private Map<Integer, Set<Session>> sessionMap = new HashMap<>();

    public WebSocketSessions() {}

    public void addSession(Integer gameID, Session session) {
        if (sessionMap.containsKey(gameID)) {
            Set<Session> sessions = sessionMap.get(gameID);
            sessions.add(session);
        } else {
            Set<Session> sessions = new HashSet<>();
            sessions.add(session);
            sessionMap.put(gameID, sessions);
        }
    }

    public void removeSession(Integer gameID, Session session) {
        Set<Session> sessions = sessionMap.get(gameID);
        sessions.remove(session);
    }

    public Set<Session> getSessionsForGame(Integer gameID) {
        return sessionMap.get(gameID);
    }
}
