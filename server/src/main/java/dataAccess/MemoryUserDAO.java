package dataaccess;


import model.*;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO {
    private HashMap<String, UserData> users = new HashMap<>();

    public MemoryUserDAO() {

    }

    public void createUser(UserData u) throws DataAccessException {
        users.put(u.username(), u);
    }

    public UserData getUser(String u) throws DataAccessException {
        return users.get(u);
    }

    public void clear() throws DataAccessException {
        users.clear();
    }

}
