package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {
    private HashMap<String, AuthData> tokens = new HashMap<>();

    @Override
    public void clear() throws DataAccessException {
        tokens.clear();
    }

    @Override
    public void createAuth(AuthData u) throws DataAccessException {
        tokens.put(u.authToken(), u);
    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException {
        return tokens.get(token);
    }

    @Override
    public void deleteAuth(String token) throws DataAccessException {
        tokens.remove(token);
    }
}
