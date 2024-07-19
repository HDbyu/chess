package dataaccess;


import model.*;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO {
    private HashMap<String, UserData> users = new HashMap<>();

    public MemoryUserDAO() {

    }

    @Override
    public void createUser(UserData u) throws DataAccessException {
        users.put(u.username(), u);
    }

    @Override
    public UserData getUser(String name) throws DataAccessException {
        return users.get(name);
    }

    @Override
    public void clear() throws DataAccessException {
        users.clear();
    }

}
