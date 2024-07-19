package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryGameDAO implements GameDAO {
    private HashMap<Integer, GameData> games = new HashMap<>();

    public MemoryGameDAO() {}

    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }

    @Override
    public void createGame(GameData u) throws DataAccessException {
        games.put(u.gameID(), u);
    }

    @Override
    public GameData getGame(int id) throws DataAccessException {
        return games.get(id);
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return games.values();
    }

    @Override
    public void updateGame(GameData u) throws DataAccessException {
        games.replace(u.gameID(), u);
    }
}
