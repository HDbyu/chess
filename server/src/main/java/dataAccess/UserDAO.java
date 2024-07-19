package dataaccess;


import model.*;

import java.util.HashMap;
import java.util.HashSet;

public class UserDAO {
    private HashMap<String, UserData> users = new HashMap<>();

    public UserDAO() {

    }

    void createUser(UserData u) throws DataAccessException {
        users.put(u.username(), u);
    }

    UserData getUser(String u) throws DataAccessException {
        return users.get(u);
    }

    void clear() throws DataAccessException {
        users.clear();
    }

}
