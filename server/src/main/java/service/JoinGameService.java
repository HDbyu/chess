package service;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import requestresult.*;

import java.util.Random;

public class JoinGameService {
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;

    public JoinGameService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public JoinGameResult joinGame(JoinGameRequest request) {
        if (request.playerColor() == null || request.gameID() == null) {
            return new JoinGameResult("Error: bad request");
        }
        AuthData authToken = null;
        GameData game;
        try {
            authToken = authDAO.getAuth(request.authorization());

            if(authToken != null) {
                game = gameDAO.getGame(request.gameID());
                if (request.playerColor().equals(ChessGame.TeamColor.WHITE)) {
                    if (game.whiteUsername() != null) {
                        return new JoinGameResult("Error: already taken");
                    }
                    gameDAO.updateGame(new GameData(game.gameID(), authDAO.getAuth(request.authorization()).username(),
                            game.blackUsername(), game.gameName(), game.game()));
                }
                if (request.playerColor().equals(ChessGame.TeamColor.BLACK)) {
                    if (game.blackUsername() != null) {
                        return new JoinGameResult("Error: already taken");
                    }
                    gameDAO.updateGame(new GameData(game.gameID(), game.whiteUsername(), authDAO.getAuth(request.authorization()).username(),
                            game.gameName(), game.game()));
                }
            } else {return new JoinGameResult("Error: unauthorized");}
        } catch (Exception e) {
            return new JoinGameResult("Error: " + e.getMessage());
        }
        return new JoinGameResult(null);
    }
}
