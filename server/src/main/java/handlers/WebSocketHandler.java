package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import websocket.WebSocketSessions;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@WebSocket
public class WebSocketHandler {

    private WebSocketSessions sessions = new WebSocketSessions();
    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public WebSocketHandler(AuthDAO auth, GameDAO game) {
        authDAO = auth;
        gameDAO = game;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        Gson gson = new Gson();
        UserGameCommand gameCommand = gson.fromJson(message, UserGameCommand.class);
        if (gameCommand.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE) {
            gameCommand = gson.fromJson(message, MakeMoveCommand.class);
        }
        AuthData auth = authDAO.getAuth(gameCommand.getAuthToken());
        GameData game = gameDAO.getGame(gameCommand.getGameID());
        if (auth != null) {
            if (game != null) {
                if (gameCommand.getCommandType() == UserGameCommand.CommandType.CONNECT) {
                    connect(auth.username(), game, session);
                }
            }
        }
    }

    private void connect(String username, GameData game, Session session) throws IOException {
        sessions.addSession(game.gameID(), session);
        Set<Session> currentSessions = sessions.getSessionsForGame(game.gameID());
        Gson gson = new Gson();
        LoadGameMessage loadMessage = new LoadGameMessage(game.game());
        session.getRemote().sendString(gson.toJson(loadMessage));
        NotificationMessage notification;
        if (Objects.equals(username, game.blackUsername())) {
            notification = new NotificationMessage(username + " has joined the game as black");
        } else if (Objects.equals(username, game.whiteUsername())) {
            notification = new NotificationMessage(username + " has joined the game as white");
        } else {
            notification = new NotificationMessage(username + " is observing the game");
        }
        if (currentSessions != null) {
            for (Session u : currentSessions) {
                if (!u.equals(session)) {
                    u.getRemote().sendString(gson.toJson(notification));
                }
            }
        }
    }
}
