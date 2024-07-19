package dataaccess;

import model.*;

import java.util.Collection;
import java.util.List;

public interface GameDAO {
    void clear() throws DataAccessException;

    void createGame(GameData u) throws DataAccessException;

    GameData getGame(int id) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;

    void updateGame(GameData u) throws DataAccessException;
}
