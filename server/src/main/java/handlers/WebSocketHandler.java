package handlers;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataaccess.*;
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
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@WebSocket
public class WebSocketHandler {

    private WebSocketSessions sessions = new WebSocketSessions();
    private SQLAuthDAO authDAO;
    private SQLGameDAO gameDAO;

    public WebSocketHandler(SQLAuthDAO auth, SQLGameDAO game) {
        authDAO = auth;
        gameDAO = game;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        Gson gson = new Gson();
        UserGameCommand gameCommand = gson.fromJson(message, UserGameCommand.class);
        AuthData auth = authDAO.getAuth(gameCommand.getAuthToken());
        GameData game = gameDAO.getGame(gameCommand.getGameID());
        if (auth != null) {
            if (game != null) {
                if (gameCommand.getCommandType() == UserGameCommand.CommandType.CONNECT) {
                    connect(auth.username(), game, session);
                }
                else if (gameCommand.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE) {
                    MakeMoveCommand moveCommand = gson.fromJson(message, MakeMoveCommand.class);
                    makeMove(auth.username(), game, session, moveCommand.getMove());
                } else if (gameCommand.getCommandType() == UserGameCommand.CommandType.LEAVE) {
                    leave(auth.username(), game, session);
                } else if (gameCommand.getCommandType() == UserGameCommand.CommandType.RESIGN) {
                    resign(auth.username(), game, session);
                }
            } else {
                ErrorMessage errorMessage = new ErrorMessage("Error: No game found");
                session.getRemote().sendString(gson.toJson(errorMessage));
            }
        } else {
            ErrorMessage errorMessage = new ErrorMessage("Error: Authorization unrecognized");
            session.getRemote().sendString(gson.toJson(errorMessage));
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
        for (Session u : currentSessions) {
            if (!u.equals(session)) {
                u.getRemote().sendString(gson.toJson(notification));
            }
        }
    }

    private void makeMove(String username, GameData game, Session session, ChessMove move) throws IOException, DataAccessException {
        Gson gson = new Gson();
        if (!game.game().getActive()) {
            ErrorMessage errorMessage = new ErrorMessage("Error: Game is not active");
            session.getRemote().sendString(gson.toJson(errorMessage));
            return;
        }
        ChessGame.TeamColor team;
        if (Objects.equals(username, game.blackUsername())) {
            team = ChessGame.TeamColor.BLACK;
        } else if (Objects.equals(username, game.whiteUsername())) {
            team = ChessGame.TeamColor.WHITE;
        } else {
            ErrorMessage errorMessage = new ErrorMessage("Error: Invalid command");
            session.getRemote().sendString(gson.toJson(errorMessage));
            return;
        }
        if (!game.game().getBoard().getPiece(move.getStartPosition()).getTeamColor().equals(team)) {
            ErrorMessage errorMessage = new ErrorMessage("Error: Invalid move");
            session.getRemote().sendString(gson.toJson(errorMessage));
            return;
        }
        try {
            game.game().makeMove(move);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage("Error: Invalid move");
            session.getRemote().sendString(gson.toJson(errorMessage));
            return;
        }
        gameDAO.updateGame(game);
        for (Session u : sessions.getSessionsForGame(game.gameID())) {
            LoadGameMessage loadMessage = new LoadGameMessage(game.game());
            u.getRemote().sendString(gson.toJson(loadMessage));
        }
        String colum;
        NotificationMessage notification = new NotificationMessage(username + " has moved " +
                game.game().getBoard().getPiece(move.getEndPosition()) + " from " +
                move.getStartPosition().getColumnLetter() + " " + move.getStartPosition().getRow() + " to "
                + move.getEndPosition().getColumnLetter() + " " + move.getEndPosition().getRow());
        for (Session u : sessions.getSessionsForGame(game.gameID())) {
            if (!u.equals(session)) {
                u.getRemote().sendString(gson.toJson(notification));
            }
        }
        boolean send = false;
        if (game.game().isInCheck(ChessGame.TeamColor.WHITE)) {
            notification = new NotificationMessage("White player " + game.whiteUsername() + " is in check");
            send = true;
        } else if (game.game().isInCheckmate(ChessGame.TeamColor.WHITE)) {
            notification = new NotificationMessage("White player " + game.whiteUsername() + " is checkmated");
            send = true;
        } else if (game.game().isInStalemate(ChessGame.TeamColor.WHITE)) {
            notification = new NotificationMessage("White player " + game.whiteUsername() + " is stalemated");
            send = true;
        } else if (game.game().isInCheck(ChessGame.TeamColor.BLACK)) {
            notification = new NotificationMessage("Black player " + game.blackUsername() + " is in check");
            send = true;
        } else if (game.game().isInCheckmate(ChessGame.TeamColor.BLACK)) {
            notification = new NotificationMessage("Black player " + game.blackUsername() + " is checkmated");
            send = true;
        } else if (game.game().isInStalemate(ChessGame.TeamColor.BLACK)) {
            notification = new NotificationMessage("Black player " + game.blackUsername() + " is stalemated");
            send = true;
        }
        if (send) {
            for (Session u : sessions.getSessionsForGame(game.gameID())) {
                u.getRemote().sendString(gson.toJson(notification));
            }
        }
    }

    private void leave(String username, GameData game, Session session) throws IOException, DataAccessException {
        if (Objects.equals(username, game.blackUsername())) {
            gameDAO.updateGame(new GameData(game.gameID(), game.whiteUsername(), null,
                    game.gameName(), game.game()));
        } else if (Objects.equals(username, game.whiteUsername())) {
            gameDAO.updateGame(new GameData(game.gameID(), null, game.blackUsername(),
                    game.gameName(), game.game()));
        }
        sessions.removeSession(game.gameID(), session);
        Gson gson = new Gson();
        NotificationMessage notification = new NotificationMessage(username + " has left the game");
        for (Session u : sessions.getSessionsForGame(game.gameID())) {
            u.getRemote().sendString(gson.toJson(notification));
        }
    }

    private void resign(String username, GameData game, Session session) throws IOException, DataAccessException {
        Gson gson = new Gson();
        if (!game.game().getActive()) {
            ErrorMessage errorMessage = new ErrorMessage("Error: Invalid command");
            session.getRemote().sendString(gson.toJson(errorMessage));
            return;
        }
        if (!Objects.equals(username, game.whiteUsername()) && !Objects.equals(username, game.blackUsername())) {
            ErrorMessage errorMessage = new ErrorMessage("Error: Invalid command");
            session.getRemote().sendString(gson.toJson(errorMessage));
            return;
        }
        game.game().setActive(false);
        gameDAO.updateGame(game);
        NotificationMessage notification = new NotificationMessage(username + " has resigned");
        for (Session u : sessions.getSessionsForGame(game.gameID())) {
            u.getRemote().sendString(gson.toJson(notification));
        }
    }
}
