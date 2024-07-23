package service;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import requestresult.*;

import java.util.Collection;
import java.util.Random;

public class CreateGameService {
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;

    public CreateGameService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public CreateGameResult createGame(CreateGameRequest request) {
        if (request.gameName().isEmpty()) {
            return new CreateGameResult(null, "Error: bad request");
        }
        AuthData authToken = null;
        int gameID;
        try {
            authToken = authDAO.getAuth(request.authorization());

            if(authToken != null) {
                gameID = new Random().nextInt();
                gameDAO.createGame(new GameData(gameID, null, null, request.gameName(), new ChessGame()));
            } else return new CreateGameResult(null, "Error: unauthorized");
        } catch (Exception e) {
            return new CreateGameResult(null, "Error: database error");
        }
        return new CreateGameResult(gameID, null);
    }
}
