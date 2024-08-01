package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.SQLGameDAO;
import model.AuthData;
import model.GameData;
import requestresult.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class ListGamesService {
    private SQLGameDAO gameDAO;
    private MemoryAuthDAO authDAO;

    public ListGamesService(SQLGameDAO gameDAO, MemoryAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public ListGamesResult listGames(ListGamesRequest request) {
        AuthData authToken = null;
        Collection<GameData> games = null;
        try {
            authToken = authDAO.getAuth(request.authorization());

            if(authToken != null) {
                games = gameDAO.listGames();
            } else {return new ListGamesResult(null, "Error: unauthorized");}
        } catch (Exception e) {
            return new ListGamesResult(null, "Error: database error");
        }
        return new ListGamesResult(games, null);
    }
}
