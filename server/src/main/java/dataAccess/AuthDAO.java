package dataaccess;

import model.AuthData;
import model.GameData;

public interface AuthDAO {
    void clear() throws DataAccessException;

    void createAuth(AuthData u) throws DataAccessException;

    AuthData getAuth(String token) throws DataAccessException;

    void deleteAuth(String token) throws DataAccessException;
}
