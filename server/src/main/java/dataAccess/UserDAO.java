package dataaccess;


import model.*;

import java.util.HashMap;
import java.util.HashSet;

public class UserDAO implements UserDAOInterface {
    private HashMap<String, UserData> users = new HashMap<>();

    public UserDAO() {

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
