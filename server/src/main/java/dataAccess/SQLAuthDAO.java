package dataaccess;

import model.AuthData;

import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO {
    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {

            try (var preparedStatement = conn.prepareStatement("DELETE tokens from SAVEPOINT ")) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {

                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void createAuth(AuthData u) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {

            try (var preparedStatement = conn.prepareStatement("SET auth, name from AuthData")) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        var auth = rs.getString("auth");
                        var name = rs.getString("name");

                        System.out.printf("auth: %d, name: %s", auth, name);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {

            try (var preparedStatement = conn.prepareStatement("SELECT authData from SAVEPOINT where auth = token")) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        var id = rs.getInt("id");
                        var name = rs.getString("name");
                        var type = rs.getString("type");

                        System.out.printf("id: %d, name: %s, type: %s%n", id, name, type);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void deleteAuth(String token) throws DataAccessException {

    }
}
