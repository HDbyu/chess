package dataaccess;

import model.AuthData;
import model.UserData;

import java.sql.SQLException;

public class SQLUserDAO implements UserDAO{

    public SQLUserDAO() throws DataAccessException{
        DatabaseManager.configureDatabase(createStatements);
    }
    @Override
    public void createUser(UserData u) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {

            try (var preparedStatement = conn.prepareStatement("INSERT INTO userData " +
                    "(username, password, email) VALUES(u.username, u.password, u.email)")) {
                preparedStatement.setString(1, u.username());
                preparedStatement.setString(2, u.password());
                preparedStatement.setString(3, u.email());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public UserData getUser(String name) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {

            try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM " +
                    "userData WHERE username =?")) {
                preparedStatement.setString(1, name);
                try (var rs = preparedStatement.executeQuery()) {
                    String username = null;
                    String password = null;
                    String email = null;
                    if (rs.next()) {
                        username = rs.getString("username");
                        password = rs.getString("password");
                        email = rs.getString("email");
                        return new UserData(username, password, email);
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
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {

            try (var preparedStatement = conn.prepareStatement("DELETE FROM userData")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  userData (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`),
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
