package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import requestresult.*;

import java.util.Collection;

public class ListGamesService {
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;

    public ListGamesService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public ListGamesResult listGames(LogoutRequest request) {
        AuthData authToken = null;
        Collection<GameData> games = null;
        try {
            authToken = authDAO.getAuth(request.authorization());

            if(authToken != null) {
                for (GameData gameData : gameDAO.listGames()) {
                    games.add(new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(),
                              gameData.gameName(), null));
                }
            } else return new ListGamesResult(null, "Error: unauthorized");
        } catch (Exception e) {
            return new ListGamesResult(null, "Error: database error");
        }
        return new ListGamesResult(games, null);
    }
}
