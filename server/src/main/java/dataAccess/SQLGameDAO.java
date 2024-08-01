package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLGameDAO implements GameDAO{

    public SQLGameDAO() throws DataAccessException{
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
        try (var conn = DatabaseManager.getConnection()) {
             Gson gson = new Gson();
            try (var preparedStatement = conn.prepareStatement("INSERT INTO gameData " +
                    "(gameID, whiteUsername, blackUsername, gameName, game) VALUES(?, ?, ?, ?, ?)")) {
                preparedStatement.setInt(1, u.gameID());
                preparedStatement.setString(2, u.whiteUsername());
                preparedStatement.setString(3, u.blackUsername());
                preparedStatement.setString(4, u.gameName());
                preparedStatement.setString(5, gson.toJson(u.game()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public GameData getGame(int id) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
        Gson gson = new Gson();
            try (var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, " +
                    "blackUsername, gameName, game FROM gameData WHERE gameID =?")) {
                preparedStatement.setInt(1, id);
                try (var rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        var gameID = rs.getInt("gameID");
                        var white = rs.getString("whiteUsername");
                        var black = rs.getString("blackUsername");
                        var gameName = rs.getString("gameName");
                        var game = gson.fromJson(rs.getString("game"), ChessGame.class);
                        return new GameData(gameID, white, black, gameName, game);
                    }
                    else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            Gson gson = new Gson();
            try (var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, " +
                    "blackUsername, gameName, game FROM gameData")) {
                try (var rs = preparedStatement.executeQuery()) {
                    Collection<GameData> games = new ArrayList<>();
                    while (rs.next()) {
                        var gameID = rs.getInt("gameID");
                        var black = rs.getString("blackUsername");
                        var white = rs.getString("whiteUsername");
                        var gameName = rs.getString("gameName");
                        var game = gson.fromJson(rs.getString("game"), ChessGame.class);
                        games.add(new GameData(gameID, white, black, gameName, game));
                    }
                    return games;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void updateGame(GameData u) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            Gson gson = new Gson();
            try (var preparedStatement = conn.prepareStatement("UPDATE gameData (gameID, whiteUsername, " +
                    "blackUsername, gameName, game) VALUES(?, ?, ?, ?, ?) WHERE gameID =?")) {
                preparedStatement.setInt(1, u.gameID());
                preparedStatement.setString(2, u.whiteUsername());
                preparedStatement.setString(3, u.blackUsername());
                preparedStatement.setString(4, u.gameName());
                preparedStatement.setString(5, gson.toJson(u.game()));
                preparedStatement.setInt(6, u.gameID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  gameData (
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
