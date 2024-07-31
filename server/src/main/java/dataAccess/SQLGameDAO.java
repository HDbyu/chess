package dataaccess;

import model.GameData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class SQLGameDAO implements GameDAO{

    SQLGameDAO() throws DataAccessException{
        DatabaseManager.configureDatabase(createStatements);
    }
    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {

            try (var preparedStatement = conn.prepareStatement("DELETE FROM gameData")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void createGame(GameData u) throws DataAccessException {

    }

    @Override
    public GameData getGame(int id) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void updateGame(GameData u) throws DataAccessException {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  gameData (
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256) NOT NULL,
              `blackUsername` varchar(256) NOT NULL,
              `gameName` varchar(256) NOT NULL,
              `game` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`),
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
