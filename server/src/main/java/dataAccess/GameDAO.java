package dataaccess;

import model.*;

import java.util.List;

public interface GameDAO {
    void clear() throws DataAccessException;

    void createGame(GameData u) throws DataAccessException;

    GameData getGame(int u) throws DataAccessException;

    List<GameData> listGames() throws DataAccessException;
}
