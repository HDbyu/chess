package service;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import requestresult.*;

import java.util.Collection;
import java.util.Random;

import static java.lang.Math.abs;

public class CreateGameService {
    private SQLGameDAO gameDAO;
    private SQLAuthDAO authDAO;

    public CreateGameService(SQLGameDAO gameDAO, SQLAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public CreateGameResult createGame(CreateGameRequest request) {
        if (request.gameName() == null) {
            return new CreateGameResult(null, "Error: bad request");
        }
        AuthData authToken = null;
        int gameID;
        try {
            authToken = authDAO.getAuth(request.authorization());

            if(authToken != null) {
                gameID = abs(new Random().nextInt());
                gameDAO.createGame(new GameData(gameID, null, null, request.gameName(), new ChessGame()));
            } else {return new CreateGameResult(null, "Error: unauthorized");}
        } catch (Exception e) {
            return new CreateGameResult(null, "Error: " + e.getMessage());
        }
        return new CreateGameResult(gameID, null);
    }
}
